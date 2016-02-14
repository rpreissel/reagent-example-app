(ns reagent-example-app.core
  (:require [reagent.core :as reagent]
            [reagent-example-app.password :as pw]))

(enable-console-print!)

(defn application []
  [:div.ApplicationView
   [pw/password-view]])


(reagent/render-component [application]
                          (. js/document (getElementById "app")))

