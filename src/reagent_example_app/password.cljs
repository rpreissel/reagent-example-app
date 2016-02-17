(ns reagent-example-app.password
  (:require [reagent.core :as reagent]
            [reagent-example-app.corecomponents :as cc]
            [reagent-example-app.restrictions :as res]))

(defn- check-restrictions [value restrictions]
  (map #(% value) restrictions))

(defn password-form [restrictions on-password-set]
  (let [password (reagent/atom "")]
    (fn [restrictions on-password-set]
      (let [checks         (check-restrictions @password restrictions)
            failed-checks  (count (filter (complement :checked) checks))
            valid-password (zero? failed-checks)]
        [:div
         [cc/initial-focus-wrapper
          [:input {:type "password"
                   :value @password
                   :placeholder "Password"
                   :on-change #(reset! password (-> % .-target .-value))}]]
         [cc/check-label-list checks]
         (if (pos? failed-checks)
           [:div.Label (str failed-checks " checks failed")]
           [:div.Label.Label-success "All checks passed"])
         [cc/button-bar
          [cc/button "Set Password" :enabled valid-password :on-click #(on-password-set @password)]]]))))


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


