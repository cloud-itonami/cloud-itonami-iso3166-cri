(ns marketentry.registry
  "Pure-function market-entry filing-draft + filing-submit record
  construction -- an append-only market-entry book-of-record draft.

  Like every sibling actor's registry, there is no single international
  reference-number standard for a public-procurement market-entry
  filing -- Costa Rica assigns its own formats (SICOP supplier-record
  identifiers, Ministerio de Hacienda cédula jurídica numbers, and
  PRODHAB's own database-registration folio numbers each have their
  own real formats this actor does not reproduce). This namespace does
  NOT invent one; it builds a jurisdiction-scoped sequence number and
  validates the record's required fields, the same honest,
  non-fabricating discipline `marketentry.facts` uses.

  `engagement-fee-matches-claim?` is an HONEST reapplication of the
  SAME ground-truth-recompute DISCIPLINE sibling actors use (verify a
  claimed monetary total against the entity's own recorded quantity x
  unit fields), reapplied to a market-entry engagement fee line.

  `prodhab-registration-scope-mismatch?` is the FLAGSHIP check for
  this vertical -- see `marketentry.facts`'s namespace docstring for
  the full grounding (Ley N.º 8968 -- the SAME citation already
  established in this repo's own `statute.facts` -- corroborated via
  DLA Piper's Data Protection Laws of the World). It INDEPENDENTLY
  verifies whether an engagement whose own declared
  `:data-handling-scope` actually TRIGGERS a PRODHAB
  database-registration duty (per `marketentry.facts/
  prodhab-registration-required?`) has actually satisfied it -- a
  CROSS-STATUTE, SCOPE-CONDITIONAL applicability check, not a
  boolean-presence check on its own, not a monetary-threshold check,
  not a multi-authority territorial-jurisdiction check.

  This namespace is pure data + pure functions -- no I/O, no network
  call to any real procurement portal (SICOP included). It builds the
  RECORD an operator would keep, not the act of submitting a portal
  registration itself (that is `marketentry.operation`'s
  `:filing/submit`, always human-gated -- see README Actuation)."
  (:require [clojure.string :as str]
            [marketentry.facts :as facts]))

(defn- unsigned-certificate
  "Every certificate this actor produces is UNSIGNED -- signature is
  the market-entry operator's act, not this actor's."
  [kind subject record-id]
  {"@context" ["https://www.w3.org/ns/credentials/v2"]
   "type" ["VerifiableCredential" kind]
   "credentialSubject" {"id" subject "record" record-id}
   "proof" nil
   "issued_by_registry" false
   "status" "draft-unsigned"})

(defn- zero-pad [n w]
  (let [s (str n)]
    (str (apply str (repeat (max 0 (- w (count s))) "0")) s)))

(defn compute-engagement-fee
  "The ground-truth engagement fee for `engagement`'s own `:base-fee`
  and `:monitoring-months` x `:monthly-rate` -- a single flat
  base + months x rate calculation, not a full pricing engine."
  [{:keys [base-fee monthly-rate monitoring-months]}]
  (+ (double base-fee)
     (* (double monthly-rate) (double monitoring-months))))

(defn engagement-fee-matches-claim?
  "Does `engagement`'s own `:claimed-fee` equal the independently
  recomputed `compute-engagement-fee`?"
  [{:keys [claimed-fee] :as engagement}]
  (== (double claimed-fee) (compute-engagement-fee engagement)))

(defn prodhab-registration-scope-mismatch?
  "FLAGSHIP check. INDEPENDENTLY verifies whether `engagement`'s own
  declared `:data-handling-scope` (e.g. `:commercialize`) actually
  requires PRODHAB database registration for `:jurisdiction` (per
  `marketentry.facts/prodhab-registration-required?`, which honestly
  accounts for the `:regulated-financial-institution?` exemption Ley
  8968 itself carves out) and, if so, whether the engagement's own
  `:prodhab-registered?` flag confirms that duty was actually
  discharged. Returns true (mismatch -- a violation) ONLY when
  registration is required and NOT on file -- never fires for a scope
  that does not trigger the duty at all (`:internal-only`), and never
  fires for an exempt regulated financial institution, even if
  unregistered."
  [{:keys [jurisdiction data-handling-scope regulated-financial-institution? prodhab-registered?]}]
  (and (facts/prodhab-registration-required?
        jurisdiction data-handling-scope regulated-financial-institution?)
       (not prodhab-registered?)))

(defn register-draft
  "Validate + construct the FILING-DRAFT registration DRAFT -- the
  market-entry operator's own act of preparing a portal registration
  package. Pure function -- does not touch any real procurement
  portal (SICOP included)."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "draft: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "draft: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "draft: sequence must be >= 0" {})))
  (let [draft-number (str (str/upper-case jurisdiction) "-DFT-" (zero-pad sequence 6))
        record {"record_id" draft-number
                "kind" "filing-draft"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "draft_number" draft-number
     "certificate" (unsigned-certificate "FilingDraft" draft-number draft-number)}))

(defn register-submit
  "Validate + construct the FILING-SUBMIT registration DRAFT -- the
  market-entry operator's own act of actually submitting a portal
  registration (always human-gated upstream)."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "submit: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "submit: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "submit: sequence must be >= 0" {})))
  (let [submit-number (str (str/upper-case jurisdiction) "-SUB-" (zero-pad sequence 6))
        record {"record_id" submit-number
                "kind" "filing-submit"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "submit_number" submit-number
     "certificate" (unsigned-certificate "FilingSubmit" submit-number submit-number)}))

(defn append [history result]
  (conj (vec history) (get result "record")))
