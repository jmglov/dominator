(ns dominator.db
  (:require [dominator.cards :as cards]))

(def default-db
  {:players []
   :supply cards/supply
   :trash cards/trash})
