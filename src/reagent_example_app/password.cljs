(ns reagent-example-app.password
  (:require [reagent.core :as reagent]
            [reagent-example-app.corecomponents :as cc]
            [reagent-example-app.restrictions :as res]))

(defn- check-restrictions [value restrictions]
  (map #(identity {:label (:label %) :checked ((:validate %) value)}) restrictions))

(defn password-form [restrictions on-password-set]
  (let [password (reagent/atom "")]
    (fn [restrictions]
      (let [checks (check-restrictions @password restrictions)
            failed-checks (reduce #(if (:checked %2) %1 (inc %1)) 0 checks)
            valid-password (= failed-checks 0)]
        [:div
         [cc/initial-focus-wrapper
          [:input {:type "password"
                   :value @password
                   :placeholder "Password"
                   :onChange #(reset! password (-> % .-target .-value))}]]
         [cc/check-label-list checks]
         (if (> failed-checks 0)
           [:div.Label (str failed-checks " checks failed")]
           [:div.Label.Label-success "All checks passed"])
         [cc/button-bar
          [cc/button "Set Password" :enabled valid-password :on-click-handler #(on-password-set @password)]]]))))


(defn password-view []
  (let [password (reagent/atom nil)]
    (fn []
      (if (nil? @password)
        [:div
         [:h1 "Step 1: Choose new password"]
         [password-form res/default-restrictions #(reset! password %)]]
        [:div
         [:h1 "Step 2: Confirm password"]
         [cc/message-dialog (str "Your new password: " @password) "Reset" #(reset! password nil)]]))))


