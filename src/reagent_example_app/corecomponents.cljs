(ns reagent-example-app.corecomponents
  (:require [reagent.core :as reagent]))


(defn check-label [label checked]
  [:div {:class (if checked "CheckLabel-checked" "CheckLabel-unchecked")} label])

(defn check-label-list [checks]
  (let [create-label (fn [{:keys [label checked]}]
                       ^{:key label}[check-label label checked])]
    [:div.CheckLabelList
     (map create-label checks)]))

(defn button [label & {:keys [enabled on-click] :or {enabled true} }]
  [:button {:disabled (not enabled) :on-click (when enabled on-click)} label])

(defn button-bar [& children]
  (into [:div.ButtonBar] children))

(defn message-dialog [message button-title on-ok-handler]
  [:div message
   [button-bar
    [button button-title :on-click on-ok-handler]]])


(defn navigation-bar [active-view-id on-click items]
  (let [create-item (fn [{:keys [view-id label]}]
                      [:li {:key view-id
                           :class (if (= view-id active-view-id)
                                    "NavigationBar-Item NavigationBar-Item-Active"
                                    "NavigationBar-Item")
                           :on-click #(on-click view-id)} label])]
    [:ul.NavigationBar
     (map create-item items)]))

(def initial-focus-wrapper
  (with-meta identity
    {:component-did-mount #(.focus (reagent/dom-node %))}))

