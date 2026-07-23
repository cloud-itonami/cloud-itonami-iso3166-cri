(ns marketentry.facts-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.facts :as facts]))

(deftest cri-has-spec-basis
  (let [sb (facts/spec-basis "CRI")]
    (is (some? sb))
    (is (string? (:provenance sb)))
    (is (seq (:required-evidence sb)))
    (is (= 4 (count (:required-evidence sb))) "exactly 4 required-evidence items, not padded")
    (is (some? (facts/corporate-number-spec-basis "CRI")))
    (is (some? (facts/prodhab-spec-basis "CRI")))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest required-evidence-satisfied
  (let [sb (facts/spec-basis "CRI")
        all (:required-evidence sb)]
    (is (true? (facts/required-evidence-satisfied? "CRI" all)))
    (is (not (facts/required-evidence-satisfied? "CRI" (take 1 all))))
    (is (nil? (facts/required-evidence-satisfied? "ATL" all)))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["CRI" "USA" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["ATL" "USA"] (:missing-jurisdictions c)))))

(deftest mercantile-evidence-reuses-statute-facts-citation
  (testing "the Registro Mercantil evidence item reuses the SAME Código de Comercio Ley N.º 3284 citation already in statute.facts, never a second different one"
    (let [sb (facts/spec-basis "CRI")]
      (is (some #(re-find #"cri\.codigo-comercio-ley-3284" %) (:required-evidence sb)))
      (is (re-find #"cri\.codigo-comercio-ley-3284" (:legal-basis sb))))))

(deftest no-fabricated-live-official-portal-fetches
  (testing "sicop.go.cr/hacienda.go.cr/rnpdigital.com were unreachable this session -- must be disclosed, never claimed as independently browsed live sources"
    (let [sb (facts/spec-basis "CRI")]
      (is (re-find #"empty client-rendered" (:provenance sb)))
      (is (re-find #"HTTP 400" (:provenance sb)))
      (is (re-find #"IP-blocklist" (:provenance sb)))
      (is (re-find #"cgr\.go\.cr" (:provenance sb))))))

(deftest prodhab-flagship-is-scope-conditional-by-basis
  (testing "the flagship basis cites Ley N.º 8968 (reused from statute.facts) and DLA Piper's corroborating fetch, and names the scope-trigger/exempt sets"
    (let [pb (facts/prodhab-spec-basis "CRI")]
      (is (re-find #"cri\.ley-proteccion-datos-8968" (:prodhab-legal-basis pb)))
      (is (re-find #"dlapiperdataprotection\.com" (:prodhab-provenance pb)))
      (is (= #{:distribute :disclose :commercialize} (:prodhab-registration-scope-triggers pb)))
      (is (= #{:internal-only} (:prodhab-exempt-scopes pb))))))

(deftest prodhab-registration-required-is-honestly-scoped
  (testing "only scopes the cited source actually names as triggering registration ever require it; an unrecognized jurisdiction never does"
    (is (true? (facts/prodhab-registration-required? "CRI" :commercialize false)))
    (is (true? (facts/prodhab-registration-required? "CRI" :distribute false)))
    (is (true? (facts/prodhab-registration-required? "CRI" :disclose false)))
    (is (false? (facts/prodhab-registration-required? "CRI" :internal-only false))
        "internal-only use never triggers the duty, per DLA Piper's own quoted text")
    (is (false? (facts/prodhab-registration-required? "CRI" :commercialize true))
        "an exempt regulated financial institution never triggers the duty, even when commercializing")
    (is (false? (facts/prodhab-registration-required? "CRI" :unknown-scope false))
        "an unrecognized scope keyword is never assumed to trigger registration")
    (is (false? (facts/prodhab-registration-required? "ATL" :commercialize false))
        "no spec-basis for the jurisdiction at all -> never required")))
