(ns reagent-example-app.core
  (:require [reagent.core :as reagent]
            [reagent-example-app.corecomponents :as cc]
            [reagent-example-app.password :as pw]
            [reagent-example-app.weather :as we]
            [reagent-example-app.chart :as ch]))

(enable-console-print!)

(def views
  ["Password Form" [pw/password-view]
   "Weather Report" [we/weather-view]
   "Chart Example" ^{:class "ApplicationView-chartView"} [ch/chart-view]])


(defn application []
  (let [current-view (reagent/atom (views 1))]
    (fn []
      (let [view @current-view
            application-view-class-names (str "ApplicationView " (-> view meta :class))]
        [:div
         [cc/navigation-bar views view #(reset! current-view %)]
         [:div {:class application-view-class-names} view]]))))


(reagent/render-component [application]
                          (. js/document (getElementById "app")))


