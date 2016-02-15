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


(defn navigation-bar [active-view-id on-click-handler items]
  (let [create-item (fn [{:keys [view-id label]}]
                      [:li {:key view-id
                           :class (if (= view-id active-view-id)
                                    "NavigationBar-Item NavigationBar-Item-Active"
                                    "NavigationBar-Item")
                           :onClick #(on-click-handler view-id)} label])]
    [:ul.NavigationBar
     (map create-item items)]))

(def initial-focus-wrapper
  (with-meta identity
    {:component-did-mount #(.focus (reagent/dom-node %))}))


