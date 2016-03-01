(ns reagent-example-app.chart
  (:require [reagent.core :as reagent]
            [reagent-example-app.corecomponents :as cc]
            [cljsjs.d3]))




(defn increment-drink-with-name [name drinks]
  (map #(if (= (:name %) name)
         (update % :count inc)
         (identity %)) drinks))

(defn increment-drink-selected! [state name]
  (swap! state update :drinks (partial increment-drink-with-name name)))


(defn render-chart [node drinks]
  (let [max-count (->> drinks (map :count) (reduce max))
        data (.. js/d3
                 (select node)
                 (selectAll "div")
                 (data (clj->js drinks)))]
    (.. data
        (enter)
        (append "div")
        (style "color" "#455A64")
        (style "margin-bottom" "10px")
        (style "padding" "10px")
        (style "background-color" "#B6B6B6"))
    (.. data
        (style "width" #(str (* (.-count %) (/ 100 max-count)) "%"))
        (text #(.-name %)))
    (.. data
        (exit)
        (remove))))

(defn chart-panel [drinks]
  (reagent/create-class
    {:component-did-mount
                   (fn [this]
                     (let [node (reagent/dom-node this)]
                       (render-chart node drinks)))

     :component-did-update
                   (fn [this]
                     (let [node (reagent/dom-node this)]
                       (let [[_ current-drinks] (reagent/argv this)] ;; we have to get the current data from argv
                         (render-chart node current-drinks))))

     :display-name "chart-panel"                            ;; for more helpful warnings & errors

     :reagent-render                                        ;; Note:  is not :render
                   (fn []
                     [:div])}))                             ;; only an anchor for d3

(defn chart-view []
  (let [state (reagent/atom {:drinks [{:name "Cola" :count 5}
                                      {:name "Bier" :count 10}
                                      {:name "Wein" :count 3}
                                      {:name "Tee" :count 7}]})]
    (fn []
      [:div
       [:h1 "Chart generator"]
       [chart-panel (:drinks @state)]
       [cc/button-bar
        (for [{:keys [name count]} (:drinks @state)]
          ^{:key name} [cc/button (str name " " count)
                        :on-click #(increment-drink-selected! state name)])]])))

