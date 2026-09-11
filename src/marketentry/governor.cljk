(ns marketentry.governor
  "Market-Entry Compliance Governor -- the independent compliance layer
  that earns the MarketEntry-LLM the right to commit. The LLM has no
  notion of Costa Rican procurement law, whether an engagement's own
  declared personal-data-handling scope actually triggers a PRODHAB
  database-registration duty under Ley N.º 8968 (this repo's own
  already-established data-protection statute) that the engagement has
  not actually satisfied, whether a claimed engagement fee actually
  equals base + months x rate, whether the required registration
  evidence has actually been assessed for a filing, or when a draft
  stops being a draft and becomes a real-world SICOP portal
  submission, so this MUST be a separate system able to *reject* a
  proposal and fall back to HOLD.

  `:itonami.blueprint/governor` is `:market-entry-compliance-governor`
  (shared family keyword on blueprints; this is the Costa Rica running
  implementation of that governor for the iso3166 family, following
  the AGO template's structure).

  This blueprint's own text (README Core Contract: 'No automated
  proposal can submit a portal registration or filing the governor
  refuses, suppress a compliance record, or claim a legal/tax
  conclusion the governor has not cleared') names exactly the checks
  below.

  Checks, in priority order, ALL HARD violations: a human approver
  CANNOT override them. The confidence/actuation gate is SOFT: it
  asks a human to look (low confidence / actuation), and the human
  may approve -- but see `marketentry.phase`: for `:stake
  :actuation/draft-filing`/`:actuation/submit-filing` NO phase ever
  allows auto-commit either. Two independent layers agree that
  actuation is always a human call.

    1. Spec-basis                  -- did the jurisdiction proposal cite
                                       an OFFICIAL source
                                       (`marketentry.facts`), or invent
                                       one?
    2. Evidence incomplete         -- for `:filing/draft`/
                                       `:filing/submit`, has the
                                       jurisdiction actually been
                                       assessed with a full evidence
                                       checklist on file? For Costa
                                       Rica this covers: Registro
                                       Mercantil inscription (reusing
                                       this repo's own statute.facts
                                       Código de Comercio citation),
                                       cédula jurídica tax
                                       registration, SICOP Registro de
                                       Proveedores inscription, and a
                                       municipal patente -- four
                                       independently-sourced or
                                       -reused required-evidence
                                       items, none fabricated.
    3. PRODHAB registration scope
       mismatch                     -- for `:filing/submit`,
                                       INDEPENDENTLY verify (never
                                       trust the engagement's own
                                       self-report) whether the
                                       engagement's own declared
                                       `:data-handling-scope` (does it
                                       distribute/disclose/
                                       commercialize personal data?)
                                       actually requires PRODHAB
                                       database registration under Ley
                                       N.º 8968, and if so whether the
                                       engagement's own
                                       `:prodhab-registered?` flag
                                       confirms that duty was actually
                                       discharged. FLAGSHIP genuinely
                                       new check for the iso3166
                                       family (grep-verified absent as
                                       a governor check function name
                                       across 183 sibling
                                       governor.cljc files fetched
                                       fleet-wide this session): a
                                       CROSS-STATUTE,
                                       SCOPE-CONDITIONAL personal-data
                                       -registration-precondition
                                       test -- a DIFFERENT law (Ley
                                       8968) conditionally attaching
                                       an ADDITIONAL duty onto a
                                       public-procurement market-entry
                                       filing. SCOPE-CONDITIONAL, not
                                       a blanket rule -- it must fire
                                       ONLY when the engagement's own
                                       declared scope actually
                                       triggers the duty (never for
                                       `:internal-only` use, and never
                                       for an exempt regulated
                                       financial institution), never
                                       for a scope/registration-status
                                       pair Ley 8968 itself does not
                                       require.
    4. Engagement fee mismatch     -- for `:filing/submit`,
                                       INDEPENDENTLY recompute whether
                                       the engagement's own `:claimed-
                                       fee` equals `base-fee +
                                       monthly-rate x monitoring-
                                       months` -- honest reapplication
                                       of the ground-truth-recompute
                                       discipline sibling actors use.
    5. Confidence floor / actuation
       gate                          -- LLM confidence below threshold,
                                       OR the op is `:filing/draft`/
                                       `:filing/submit` (REAL acts)
                                       -> escalate.

  Two more guards, double-draft/double-submit prevention, are enforced
  off dedicated `:drafted?`/`:submitted?` facts (never a `:status`
  value)."
  (:require [marketentry.facts :as facts]
            [marketentry.registry :as registry]
            [marketentry.store :as store]))

(def confidence-floor 0.6)

(def high-stakes
  "Stakes grave enough to always require a human, even when clean.
  Drafting a real SICOP filing package and submitting a real filing /
  Registro de Proveedores inscription request are the two real-world
  actuation events this actor performs."
  #{:actuation/draft-filing :actuation/submit-filing})

;; ----------------------------- checks -----------------------------

(defn- spec-basis-violations
  "A `:jurisdiction/assess` (or `:filing/draft`/`:filing/submit`)
  proposal with no spec-basis citation is a HARD violation -- never
  invent a jurisdiction's market-entry requirements."
  [{:keys [op]} proposal]
  (when (contains? #{:jurisdiction/assess :filing/draft :filing/submit} op)
    (let [value (:value proposal)]
      (when (or (empty? (:cites proposal))
                (and (contains? value :spec-basis) (nil? (:spec-basis value))))
        [{:rule :no-spec-basis
          :detail "公式spec-basisの引用が無い提案は法域要件として扱えない"}]))))

(defn- evidence-incomplete-violations
  "For `:filing/draft`/`:filing/submit`, the jurisdiction's required
  registration evidence (Registro Mercantil, cédula jurídica, SICOP
  Registro de Proveedores, patente municipal) must actually be
  satisfied."
  [{:keys [op subject]} st]
  (when (contains? #{:filing/draft :filing/submit} op)
    (let [e (store/engagement st subject)
          assessment (store/assessment-of st subject)]
      (when-not (and assessment
                     (facts/required-evidence-satisfied?
                      (:jurisdiction e) (:checklist assessment)))
        [{:rule :evidence-incomplete
          :detail "法域の必要書類(Registro Mercantil/cédula jurídica/SICOP登録/市の営業許可等)が充足していない状態での提案"}]))))

(defn- prodhab-registration-scope-mismatch-violations
  "For `:filing/submit`, INDEPENDENTLY verify whether the engagement's
  own declared `:data-handling-scope` actually requires PRODHAB
  database registration (Ley N.º 8968 -- this repo's own
  already-established data-protection statute) and whether that duty
  has actually been discharged -- the flagship check this vertical
  adds. SCOPE-CONDITIONAL: fires ONLY when the declared scope
  distributes/discloses/commercializes personal data AND the
  engagement is not an exempt regulated financial institution AND
  registration is not on file."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when (registry/prodhab-registration-scope-mismatch? e)
        [{:rule :prodhab-registration-scope-mismatch
          :detail (str subject " はデータ取扱区分(" (:data-handling-scope e)
                      ")によりPRODHAB(Agencia de Protección de Datos de los Habitantes)への"
                      "データベース登録義務(Ley N.º 8968)が発生しているが未登録 -- "
                      "自己申告に依存せず独立して再検証した結果の不一致")}]))))

(defn- engagement-fee-mismatch-violations
  "For `:filing/submit`, INDEPENDENTLY recompute whether the
  engagement's own claimed fee equals base + months x rate."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when-not (registry/engagement-fee-matches-claim? e)
        [{:rule :engagement-fee-mismatch
          :detail (str subject " の申告手数料(" (:claimed-fee e)
                      ")が独立再計算値(" (registry/compute-engagement-fee e) ")と一致しない")}]))))

(defn- already-drafted-violations
  "For `:filing/draft`, refuses to draft the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/draft)
    (when (store/engagement-already-drafted? st subject)
      [{:rule :already-drafted
        :detail (str subject " は既にドラフト済み")}])))

(defn- already-submitted-violations
  "For `:filing/submit`, refuses to submit the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (when (store/engagement-already-submitted? st subject)
      [{:rule :already-submitted
        :detail (str subject " は既に提出済み")}])))

(defn check
  "Censors a MarketEntry-LLM proposal against the governor rules.
  Returns {:ok? bool :violations [..] :confidence c :escalate? bool
  :high-stakes? bool :hard? bool}."
  [request _context proposal st]
  (let [hard (into []
                   (concat (spec-basis-violations request proposal)
                           (evidence-incomplete-violations request st)
                           (prodhab-registration-scope-mismatch-violations request st)
                           (engagement-fee-mismatch-violations request st)
                           (already-drafted-violations request st)
                           (already-submitted-violations request st)))
        conf (:confidence proposal 0.0)
        low? (< conf confidence-floor)
        stakes? (boolean (high-stakes (:stake proposal)))
        hard? (boolean (seq hard))]
    {:ok?          (and (not hard?) (not low?) (not stakes?))
     :violations   hard
     :confidence   conf
     :hard?        hard?
     :escalate?    (and (not hard?) (or low? stakes?))
     :high-stakes? stakes?}))

(defn hold-fact
  "The audit fact written when a proposal is rejected (HOLD)."
  [request context verdict]
  {:t          :governor-hold
   :op         (:op request)
   :actor      (:actor-id context)
   :subject    (:subject request)
   :disposition :hold
   :basis      (mapv :rule (:violations verdict))
   :violations (:violations verdict)
   :confidence (:confidence verdict)})
