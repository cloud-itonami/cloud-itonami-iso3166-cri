(ns culture.facts
  "Country-level regional-culture catalog for Costa Rica (CRI) -- national
  dishes, protected products, beverages, crafts, festivals and heritage
  sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"CRI"
   [{:culture/id "cri.dish.gallo-pinto"
     :culture/name "Gallo pinto"
     :culture/country "CRI"
     :culture/kind :dish
     :culture/summary "Traditional rice-and-bean dish important to both Nicaragua and Costa Rica, both of which consider it a national dish; its precise origin is disputed between the two countries."
     :culture/url "https://en.wikipedia.org/wiki/Gallo_pinto"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "cri.dish.casado"
     :culture/name "Casado"
     :culture/country "CRI"
     :culture/kind :dish
     :culture/summary "Costa Rican meal of rice, black beans, plantains, salad, tortilla and an optional protein such as chicken, beef or fish."
     :culture/url "https://en.wikipedia.org/wiki/Casado"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "cri.dish.chifrijo"
     :culture/name "Chifrijo"
     :culture/country "CRI"
     :culture/kind :dish
     :culture/summary "Combination of chicharrón and frijoles (beans) served with rice and pico de gallo, popular in Costa Rican bars."
     :culture/url "https://en.wikipedia.org/wiki/Costa_Rican_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "cri.dish.olla-de-carne"
     :culture/name "Olla de carne"
     :culture/country "CRI"
     :culture/kind :dish
     :culture/summary "Costa Rican stew containing beef, cassava, potatoes, maize, green plantains and squash or chayote."
     :culture/url "https://en.wikipedia.org/wiki/Costa_Rican_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "cri.beverage.guaro"
     :culture/name "Guaro"
     :culture/country "CRI"
     :culture/kind :beverage
     :culture/summary "Clear distilled sugar-cane spirit popular across Latin America; Costa Rica nationalized its manufacture in 1851, and the Fábrica Nacional de Licores has produced the only legal brand, Cacique Guaro, since 1980."
     :culture/url "https://en.wikipedia.org/wiki/Guaro_(drink)"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "cri.product.costa-rican-coffee"
     :culture/name "Costa Rican coffee"
     :culture/country "CRI"
     :culture/kind :product
     :culture/summary "Coffee production has played a key role in Costa Rica's history and economy, having been the country's number-one cash crop export for several decades."
     :culture/url "https://en.wikipedia.org/wiki/Coffee_production_in_Costa_Rica"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "cri.heritage.cocos-island"
     :culture/name "Cocos Island"
     :culture/name-local "Isla del Coco"
     :culture/country "CRI"
     :culture/kind :heritage
     :culture/summary "Volcanic island in the Pacific Ocean administered by Costa Rica; Cocos Island National Park became a UNESCO World Heritage Site in 1997."
     :culture/url "https://en.wikipedia.org/wiki/Cocos_Island"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

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
      :note (str "cloud-itonami-iso3166-cri culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "CRI"))
                 " CRI entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
