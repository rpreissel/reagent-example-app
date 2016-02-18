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

(defn message-dialog [message button-title on-click]
  [:div message
   [button-bar
    [button button-title :on-click on-click]]])


(defn- create-navigation-item [[label view] active-view on-click]
  [:li {:key view
        :class (if (= view active-view)
                 "NavigationBar-Item NavigationBar-Item-Active"
                 "NavigationBar-Item")
        :on-click #(on-click view)} label])

(defn navigation-bar [views active-view on-click]
  {:pre [(even? (count views))]}
  (let [ view-pairs (partition 2 views)]
    [:ul.NavigationBar
     (map #(create-navigation-item % active-view on-click) view-pairs)]))

(def initial-focus-wrapper
  (with-meta identity
    {:component-did-mount #(.focus (reagent/dom-node %))}))

