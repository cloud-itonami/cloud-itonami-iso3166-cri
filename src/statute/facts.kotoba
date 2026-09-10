(ns statute.facts
  "General-law compliance catalog for Costa Rica (CRI). Like
  cloud-itonami-iso3166-ury, this repo had no `marketentry.facts`
  implementation yet (blueprint-only) -- this is the FIRST code-bearing
  content in this repo, self-contained with its own deps.edn. Mirrors
  cloud-itonami-iso3166-jpn/-usa/-esp/-swe/-nor/-dnk/-fin/-prt/-bel/-bra/-mex/-chl/-arg/-zaf/-col/-ury's
  `statute.facts` (ADR-2607141700, cloud-itonami-compliance-fact-federation).

  Every entry cites an OFFICIAL pgrweb.go.cr (SCIJ -- Sistema
  Costarricense de Información Jurídica, maintained by the
  Procuraduría General de la República) URL -- never fabricated. A law
  not in this table has NO spec-basis, full stop; extend `catalog`, do
  not invent an id/url.")

(def catalog
  "iso3 -> vector of statute entries."
  {"CRI"
   [{:statute/id "cri.codigo-comercio-ley-3284"
     :statute/title "Código de Comercio (Ley N.º 3284)"
     :statute/jurisdiction "CRI"
     :statute/kind :law
     :statute/law-number "Ley N.º 3284"
     :statute/url "https://pgrweb.go.cr/scij/Busqueda/Normativa/Normas/nrm_texto_completo.aspx?param1=NRTC&nValor1=1&nValor2=6239&nValor3=89980&strTipM=TC"
     :statute/url-provenance :official-pgrweb-go-cr
     :statute/enacted-date "1964-04-30"
     :statute/last-revised-date "2012-09-10"
     :statute/retrieved-at "2026-07-16"
     :statute/topic #{:corporate-governance :incorporation}}
    {:statute/id "cri.ley-proteccion-datos-8968"
     :statute/title "Ley de Protección de la Persona frente al Tratamiento de sus Datos Personales (Ley N.º 8968)"
     :statute/jurisdiction "CRI"
     :statute/kind :law
     :statute/law-number "Ley N.º 8968"
     :statute/url "https://pgrweb.go.cr/scij/Busqueda/Normativa/Normas/nrm_texto_completo.aspx?param1=NRTC&nValor1=1&nValor2=70975&nValor3=85989"
     :statute/url-provenance :official-pgrweb-go-cr
     :statute/enacted-date "2011-07-07"
     :statute/retrieved-at "2026-07-16"
     :statute/topic #{:data-protection :privacy}}
    {:statute/id "cri.codigo-trabajo-ley-2"
     :statute/title "Código de Trabajo (Ley N.º 2)"
     :statute/jurisdiction "CRI"
     :statute/kind :law
     :statute/law-number "Ley N.º 2"
     :statute/url "https://pgrweb.go.cr/scij/Busqueda/Normativa/Normas/nrm_texto_completo.aspx?nValor1=1&nValor2=8045"
     :statute/url-provenance :official-pgrweb-go-cr
     :statute/enacted-date "1943-08-27"
     :statute/last-revised-date "2026-04-07"
     :statute/retrieved-at "2026-07-16"
     :statute/topic #{:labor :employment}}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-cri statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "CRI")) " CRI statutes seeded with an "
                 "official pgrweb.go.cr citation. Extend "
                 "`statute.facts/catalog`, never fabricate a law-id or URL.")})))

(defn by-topic [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))
