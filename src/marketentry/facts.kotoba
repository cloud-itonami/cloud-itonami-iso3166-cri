(ns marketentry.facts
  "Costa Rica (CRI) market-entry catalog.

  Every field traces to a source independently fetched THIS session
  (2026-07-23), or reuses a citation THIS REPO already independently
  established and verified in a PRIOR session
  (`src/statute/facts.cljc`, retrieved 2026-07-16) -- never a second,
  different citation for the same underlying law.

    - Business/mercantile registration: this catalog REUSES this
      repo's own `statute.facts/catalog` entry
      `cri.codigo-comercio-ley-3284` (Código de Comercio, Ley N.º
      3284) verbatim, rather than re-deriving a second citation for
      the same law. THIS session independently RE-CONFIRMED that
      citation's underlying record is still live and continuous (not
      merely trusted from 2026-07-16): fetching the EXACT URL already
      on file in `statute.facts`
      (pgrweb.go.cr/scij/Busqueda/Normativa/Normas/nrm_texto_completo.aspx?param1=NRTC&nValor1=1&nValor2=6239&nValor3=89980&strTipM=TC)
      returned an HTTP 302 redirect to
      sinalevi.go.cr/ResultadosNormativa/Informacion?param1=6239&param2=89980&param3=1&param4=
      -- i.e. Costa Rica's SCIJ (Sistema Costarricense de Información
      Jurídica) has migrated its canonical public domain from
      pgrweb.go.cr to sinalevi.go.cr WHILE PRESERVING the same
      internal norm identifiers (6239/89980) already cited in this
      repo's own `statute.facts`. The final sinalevi.go.cr page
      content itself could NOT be read this session -- TLS
      certificate validation failed ('unable to verify the first
      certificate') -- disclosed, not concealed; this catalog does
      not claim to have re-read the Código de Comercio's full text
      live today, only that the existing citation's underlying record
      is confirmed continuous across the domain migration.
    - Data protection: this catalog REUSES this repo's own
      `statute.facts/catalog` entry `cri.ley-proteccion-datos-8968`
      (Ley de Protección de la Persona frente al Tratamiento de sus
      Datos Personales, Ley N.º 8968) verbatim. That law's designated
      enforcement authority, PRODHAB (Agencia de Protección de Datos
      de los Habitantes), and its DATABASE-REGISTRATION mechanism --
      this catalog's FLAGSHIP grounding, see below -- were
      independently corroborated THIS session via DLA Piper's 'Data
      Protection Laws of the World' comparative-law reference
      (https://www.dlapiperdataprotection.com/index.html?t=law&c=CR
      and ?t=authority&c=CR, fetched and read directly 2026-07-23,
      own text quoted verbatim in `catalog` below). A direct fetch of
      prodhab.go.cr itself returned HTTP 403 Forbidden this session,
      and the Spanish Wikipedia article on PRODHAB does not exist
      (404 at es.wikipedia.org/wiki/Agencia_de_Protección_de_Datos_de_los_Habitantes)
      -- both disclosed, not concealed. DLA Piper's page is cited as
      the session's actually-read corroborating source for the
      registration-trigger/exemption MECHANISM; the underlying LAW
      citation remains the SAME Ley N.º 8968 already established in
      `statute.facts`, never a second, different one.
    - Public-procurement oversight: Contraloría General de la
      República (CGR), Costa Rica's independent supreme audit
      institution, WAS successfully and directly fetched this session
      (https://www.cgr.go.cr/, 2026-07-23): its own homepage confirms
      an active public-procurement-oversight role, naming 'Umbrales
      para determinar el procedimiento de contratación' (thresholds
      determining which procurement procedure applies) and 'Recursos
      de objeción y apelación' (bidder objection/appeal handling) as
      live functions, evidenced by featured coverage of CGR's own
      resolution of a dispute in ICE's 5G network licensing process.
      This catalog cites CGR ONLY at this confirmed
      institutional/oversight level -- it does NOT claim CGR
      operates/administers SICOP itself (that specific operational
      claim was not independently confirmed this session), and it
      does NOT pin a specific procurement-law decree/article number
      (e.g. Ley N.º 9986 vs the older Ley N.º 7494) as independently
      fetched-and-read this session -- an honest scoping gap, not a
      fabricated citation.
    - SICOP (Sistema Integrado de Compras Públicas) -- Costa Rica's
      e-procurement portal, already named in this repo's PRE-EXISTING
      `docs/business-model.md` (covering central-government
      ministries, autonomous institutions and municipalities,
      including ICE/CCSS/INS). This session could NOT independently
      re-verify SICOP's own legal basis or browse its live content: a
      direct fetch of sicop.go.cr returned only an empty
      client-side-rendered application shell ('SICOP' heading, no
      body text) -- disclosed, not concealed. This catalog references
      SICOP institutionally, consistent with the pre-existing
      `business-model.md` description, WITHOUT inventing a
      decree/article number for it.
    - Tax registration (cédula jurídica): cited institutionally only.
      hacienda.go.cr (Ministerio de Hacienda) returned HTTP 400 Bad
      Request on every root/subpage path attempted this session --
      disclosed, not concealed. No specific law/decree/article number
      for the cédula jurídica mandatory-registration requirement is
      asserted as independently confirmed this session -- an honest
      scoping gap, the SAME discipline this fleet's Honduras catalog
      uses for its own SAR RTN Código Tributario article-number gap.
    - Registro Nacional digital portal (rnpdigital.com): a direct
      fetch was REFUSED this session with an explicit message that
      the requesting IP address may be on an international blocklist
      ('Probablemente la dirección IP asignada a usted por su
      proveedor de servicios de internet ha sido incluida a nivel
      internacional en alguna lista negra') -- disclosed, not
      concealed. Per this workspace's hard safety rule against
      bypassing any bot-detection/access-blocking mechanism, no
      attempt was made to circumvent this refusal, and rnpdigital.com
      is not cited as a spec-basis source below.
    - Other sources attempted and UNREACHABLE this session, disclosed
      for completeness and never cited as a spec-basis source:
      Ministerio de Justicia y Paz (mjp.go.cr, 404 on the guessed
      Registro Nacional subpage -- Registro Nacional's supervising
      ministry, per this repo's own pre-existing `organization.edn`
      lineage), PROCOMER (procomer.com, 403 Forbidden), Asamblea
      Legislativa (asamblea.go.cr, connection refused), La
      Gaceta/Imprenta Nacional (imprentanacional.go.cr, 403
      Forbidden), Santander Trade's Costa Rica company-registration
      guide (TLS certificate expired), Biz Latin Hub's Costa Rica
      company-registration guide (DNS resolution failed), and a
      guessed IDB SICOP page (404).

  Explicitly NOT claimed here (fabrication traps this catalog
  avoids): no invented decree/article number for the cédula jurídica
  requirement or for SICOP's own enabling legislation; no claim of
  having independently browsed sicop.go.cr, hacienda.go.cr,
  rnpdigital.com or prodhab.go.cr live content this session; no
  foreign-company permanent-representative sub-schema (unlike
  Honduras's independently-confirmed Código de Comercio Art. 308
  numeral III finding) is asserted for Costa Rica, because this
  session did not independently verify a specific Código de Comercio
  (Ley N.º 3284) article number for such a requirement -- the reused
  `statute.facts` citation is extended only within its ALREADY
  -established scope (`#{:corporate-governance :incorporation}`), not
  stretched to cover a new unverified claim; and no
  territorial-jurisdiction-mismatch check (Honduras's flagship shape)
  is built for Costa Rica, because this session found no evidence
  Costa Rica's Registro Nacional is anything other than the single
  national registry this repo's own pre-existing `organization.edn` /
  `docs/business-model.md` already implies (one Ministerio de
  Justicia y Paz-supervised institution, never described as
  department-scoped anywhere in this repo or in any source reachable
  this session) -- building a decentralization-mismatch check on an
  undecentralized registry would itself be a fabricated premise. This
  catalog's flagship check targets a genuinely different,
  independently-corroborated mechanism instead: whether an
  engagement's own declared personal-data handling scope triggers a
  PRODHAB database-registration duty under Ley N.º 8968 that the
  engagement has not actually satisfied (`marketentry.registry`'s
  `prodhab-registration-scope-mismatch?`, ADR-2607141700 family).

  A jurisdiction not in `catalog` has NO spec-basis, full stop --
  extend `catalog`, never invent an owner-authority/legal-basis/URL.")

(def catalog
  "iso3 -> requirement map. `:required-evidence` lists the DISTINCT
  regulatory-authority-level items this session independently
  confirmed or reused from this repo's own prior-session statute
  citations (not every granular sub-form each authority's own
  checklist enumerates -- the SAME honest-scoping discipline sibling
  catalogs use). `:prodhab-*` grounds this vertical's FLAGSHIP
  governor check (`prodhab-registration-scope-mismatch?` in
  `marketentry.registry`) -- a CROSS-STATUTE, SCOPE-CONDITIONAL
  personal-data-registration-precondition check: does an engagement
  whose OWN declared data-handling scope (distribute/disclose/
  commercialize personal data) actually have the PRODHAB database
  registration Ley N.º 8968 requires for that scope, INDEPENDENTLY of
  the engagement's self-reported status, and honestly NON-firing for
  the scopes/exemptions the law itself carves out (internal-only use;
  regulated financial institutions). A check SHAPE genuinely different
  from every prior sibling in this family: not a same-branch
  eligibility/routing/threshold test (Nicaragua's cross-branch
  certification gate), not a sector-conditional constitutional
  restriction (Panama's Art. 288 retail-trade check), not a
  multi-authority TERRITORIAL-jurisdiction consistency test
  (Honduras's Cámara de Comercio check), but a cross-statute
  applicability gate: a DIFFERENT law (Ley 8968, this repo's own
  already-established data-protection statute) conditionally attaches
  an ADDITIONAL registration duty onto a public-procurement
  market-entry engagement, and this check verifies that duty was
  actually discharged when -- and only when -- the engagement's own
  declared scope triggers it."
  {"CRI"
   {:name "Republic of Costa Rica"
    :owner-authority "Contraloría General de la República (CGR) -- independent supreme audit institution with public-procurement oversight (confirmed via cgr.go.cr this session, 2026-07-23: 'Umbrales para determinar el procedimiento de contratación', 'Recursos de objeción y apelación', featured coverage of CGR's own resolution of the ICE 5G licensing dispute)"
    :legal-basis "Código de Comercio (Ley N.º 3284) -- the SAME citation as this repo's own statute.facts/cri.codigo-comercio-ley-3284, reused verbatim, never a second different one -- for the underlying business/mercantile-registration precondition; this catalog does NOT independently pin a specific public-procurement-law decree/article number (e.g. Ley N.º 9986 vs the older Ley N.º 7494) as fetched-and-read this session -- an honest scoping gap, not a fabricated citation"
    :national-spec "SICOP (Sistema Integrado de Compras Públicas) -- Costa Rica's integrated e-procurement portal per this repo's own pre-existing docs/business-model.md (central-government ministries, autonomous institutions and municipalities, including ICE/CCSS/INS); referenced institutionally only -- sicop.go.cr returned an empty client-rendered application shell this session (disclosed, not concealed), no decree/article number is asserted as independently confirmed"
    :provenance "https://www.cgr.go.cr/ (fetched and read directly, 2026-07-23) ; https://pgrweb.go.cr/scij/Busqueda/Normativa/Normas/nrm_texto_completo.aspx?param1=NRTC&nValor1=1&nValor2=6239&nValor3=89980&strTipM=TC redirect-confirmed continuous with sinalevi.go.cr/ResultadosNormativa/Informacion?param1=6239&param2=89980&param3=1&param4= this session (final page unreadable this session -- TLS certificate error, disclosed) ; sicop.go.cr returned an empty client-rendered shell this session (disclosed) ; hacienda.go.cr returned HTTP 400 on every attempt this session (disclosed) ; rnpdigital.com refused this session with an IP-blocklist message (disclosed, not bypassed, per this workspace's hard safety rule)"
    :required-evidence ["Registro Mercantil inscription with the Registro Nacional (Registro Público de Comercio), per Código de Comercio Ley N.º 3284 (the SAME citation as this repo's own statute.facts/cri.codigo-comercio-ley-3284)"
                         "Cédula jurídica tax-registration record with the Ministerio de Hacienda (institutional-level citation only -- hacienda.go.cr returned HTTP 400 this session, no specific decree/article number asserted)"
                         "SICOP (Sistema Integrado de Compras Públicas) Registro de Proveedores inscription (institutional-level citation only -- sicop.go.cr returned an empty client-rendered shell this session, no specific decree number asserted)"
                         "Patente municipal (municipal commercial-operation permit) record for the engagement's declared business location (institutional-level citation only -- general Costa Rican municipal-law requirement, no specific canton ordinance independently confirmed this session)"]
    ;; corporate tax-id sub-schema -- mirrors the AGO template's
    ;; `:corporate-number-*` triple sibling actors use.
    :corporate-number-owner-authority "Ministerio de Hacienda (Dirección General de Tributación)"
    :corporate-number-legal-basis "Cédula jurídica tax-registration record -- institutional-level citation only; hacienda.go.cr returned HTTP 400 Bad Request on every fetch attempt this session, so no specific law/decree/article number is asserted as independently confirmed (honest scoping gap, not fabricated)"
    :corporate-number-provenance "https://www.hacienda.go.cr/ (HTTP 400, this session, 2026-07-23) -- disclosed as unreachable this session, not concealed"
    ;; flagship: cross-statute, scope-conditional PRODHAB
    ;; personal-data-database-registration precondition. Genuinely new
    ;; for this vertical (grep-verified absent as a governor check
    ;; function name across 183 sibling governor.cljc files fetched
    ;; fleet-wide this session).
    :prodhab-owner-authority "Agencia de Protección de Datos de los Habitantes (PRODHAB)"
    :prodhab-legal-basis "Ley de Protección de la Persona frente al Tratamiento de sus Datos Personales, Ley N.º 8968 -- the SAME citation as this repo's own statute.facts/cri.ley-proteccion-datos-8968, never a second different one. Per DLA Piper Data Protection Laws of the World (fetched and read directly this session, 2026-07-23): 'companies that manage databases containing personal information and that distribute, disclose or commercialize such personal information in any manner must register with the Agency'; 'entities that manage databases containing personal information for internal purposes do not need to be registered with PRODHAB'; financial institutions under separate regulatory oversight are also exempt."
    ;; scope keyword -> does declaring THIS scope trigger the PRODHAB
    ;; registration duty? Mirrors camara-covers-department?'s
    ;; honest-absence discipline in spirit: only scopes the cited
    ;; source ACTUALLY names as triggering registration are members
    ;; here -- an unrecognized scope keyword is NEVER assumed to
    ;; trigger (or not trigger) registration by silent default; see
    ;; `prodhab-registration-required?` below.
    :prodhab-registration-scope-triggers #{:distribute :disclose :commercialize}
    :prodhab-exempt-scopes #{:internal-only}
    :prodhab-provenance "https://www.dlapiperdataprotection.com/index.html?t=law&c=CR ; https://www.dlapiperdataprotection.com/index.html?t=authority&c=CR (both fetched and read directly, 2026-07-23) -- prodhab.go.cr itself returned HTTP 403 Forbidden this session and the Spanish Wikipedia article on PRODHAB does not exist (404); DLA Piper is cited as the session's actually-read corroborating source for the registration-trigger/exemption mechanism, not as the primary official text"}})

(defn spec-basis
  "The jurisdiction's requirement map, or nil -- nil means NO
  spec-basis, and the governor must hold any proposal that tries to
  assess or file on it."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report: how many of the requested jurisdictions
  actually have a spec-basis entry. Never report a missing
  jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-cri marketentry.facts R0: " (count catalog)
                 " jurisdiction seeded with an official spec-basis. "
                 "This is a starting catalog for market-entry navigation, "
                 "not a survey of all ~194 jurisdictions -- extend "
                 "`marketentry.facts/catalog`, never fabricate a "
                 "jurisdiction's requirements.")})))

(defn required-evidence-satisfied?
  "Does `submitted` (a set/coll of evidence keywords or strings)
  satisfy every evidence item listed for `iso3`? Missing spec-basis ->
  never satisfied."
  [iso3 submitted]
  (when-let [{:keys [required-evidence]} (spec-basis iso3)]
    (let [need (count required-evidence)
          have (count (filter (set submitted) required-evidence))]
      (= need have))))

(defn evidence-checklist [iso3] (:required-evidence (spec-basis iso3) []))

(defn corporate-number-spec-basis
  "The jurisdiction's corporate-number / tax-id regime, or nil."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:corporate-number-owner-authority sb)
      (select-keys sb [:corporate-number-owner-authority
                        :corporate-number-legal-basis
                        :corporate-number-provenance]))))

(defn prodhab-spec-basis
  "Spec-basis for the flagship cross-statute PRODHAB
  personal-data-database-registration check -- used by the governor's
  flagship check to cite an official basis (and the confirmed
  scope-trigger/exemption sets) rather than assert a mismatch bare."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:prodhab-owner-authority sb)
      (select-keys sb [:prodhab-owner-authority
                        :prodhab-legal-basis
                        :prodhab-registration-scope-triggers
                        :prodhab-exempt-scopes
                        :prodhab-provenance]))))

(defn prodhab-registration-required?
  "Does `data-handling-scope` (one of `:internal-only`/`:distribute`/
  `:disclose`/`:commercialize`) actually trigger a PRODHAB
  database-registration duty for `iso3`, given whether the engagement
  is a `regulated-financial-institution?` (Ley 8968's own carved-out
  exemption)? An unrecognized/nil scope, or a jurisdiction with no
  spec-basis at all, is NEVER assumed to trigger registration by
  silent default -- returns false, the same honest-absence discipline
  `spec-basis` itself uses for an unlisted jurisdiction."
  [iso3 data-handling-scope regulated-financial-institution?]
  (boolean
   (when-let [{:keys [prodhab-registration-scope-triggers]} (prodhab-spec-basis iso3)]
     (and (contains? prodhab-registration-scope-triggers data-handling-scope)
          (not regulated-financial-institution?)))))
