(ns reagent-example-app.corecomponents
  (:require [reagent.core :as reagent]))


(defn check-label [label checked]
  [:div {:class (if checked "CheckLabel-checked" "CheckLabel-unchecked")} label])

(defn check-label-list [checks]
  (let [create-label (fn [{:keys [label checked]}]
                       ^{:key label}[check-label label checked])]
    [:div.CheckLabelList
      (map create-label checks)]))

(defn button [label & {:keys [enabled on-click-handler] :or {enabled true} }]
  [:button {:disabled (not enabled) :onClick (when enabled on-click-handler)} label])

(defn button-bar [& children]
  (into [:div.ButtonBar] children))

(defn message-dialog [message button-title on-ok-handler]
  [:div message
   [button-bar
    [button button-title :on-click-handler on-ok-handler]]])

(def initial-focus-wrapper
  (with-meta identity
    {:component-did-mount #(.focus (reagent/dom-node %))}))


