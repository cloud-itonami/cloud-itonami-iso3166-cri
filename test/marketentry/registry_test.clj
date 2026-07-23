(ns marketentry.registry-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.registry :as registry]))

(deftest engagement-fee-recompute
  (let [e {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 860000.0}]
    (is (== 860000.0 (registry/compute-engagement-fee e)))
    (is (true? (registry/engagement-fee-matches-claim? e))))
  (let [bad {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 999000.0}]
    (is (false? (registry/engagement-fee-matches-claim? bad)))))

(deftest prodhab-registration-scope-mismatch-flagship
  (testing "a commercialize-scope engagement not registered with PRODHAB is a mismatch"
    (is (true? (registry/prodhab-registration-scope-mismatch?
                {:jurisdiction "CRI" :data-handling-scope :commercialize
                 :regulated-financial-institution? false :prodhab-registered? false}))))
  (testing "a commercialize-scope engagement that IS registered is NOT a mismatch"
    (is (false? (registry/prodhab-registration-scope-mismatch?
                 {:jurisdiction "CRI" :data-handling-scope :commercialize
                  :regulated-financial-institution? false :prodhab-registered? true}))))
  (testing "internal-only scope never triggers the duty, registered or not"
    (is (false? (registry/prodhab-registration-scope-mismatch?
                 {:jurisdiction "CRI" :data-handling-scope :internal-only
                  :regulated-financial-institution? false :prodhab-registered? false}))))
  (testing "an exempt regulated financial institution is never a mismatch, even unregistered"
    (is (false? (registry/prodhab-registration-scope-mismatch?
                 {:jurisdiction "CRI" :data-handling-scope :distribute
                  :regulated-financial-institution? true :prodhab-registered? false}))))
  (testing "no spec-basis for the jurisdiction -> never a mismatch"
    (is (false? (registry/prodhab-registration-scope-mismatch?
                 {:jurisdiction "ATL" :data-handling-scope :commercialize
                  :regulated-financial-institution? false :prodhab-registered? false})))))

(deftest register-draft-and-submit
  (let [d (registry/register-draft "eng-1" "CRI" 0)
        s (registry/register-submit "eng-1" "CRI" 0)]
    (is (= "CRI-DFT-000000" (get d "draft_number")))
    (is (= "CRI-SUB-000000" (get s "submit_number")))
    (is (nil? (get-in d ["certificate" "proof"])))
    (is (= "draft-unsigned" (get-in s ["certificate" "status"])))))

(deftest register-requires-ids
  (is (thrown? Exception (registry/register-draft "" "CRI" 0)))
  (is (thrown? Exception (registry/register-submit "eng-1" "" 0))))
