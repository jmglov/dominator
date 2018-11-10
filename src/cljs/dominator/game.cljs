(ns dominator.game
  (:require [dominator.cards :as cards]))

(defn update-count [cards target-card f]
  (map (fn [{:keys [card] :as current}]
         (if (= target-card card)
           (update current :count f)
           current))
       cards))

(defn adjust-count [cards target-card num]
  (update-count cards target-card #(+ % num)))

(defn set-count [cards target-card num]
  (update-count cards target-card (constantly num)))

(defn adjust-victory-count [cards num-players]
  (let [num-victory-cards (if (= 2 num-players) 8 12)]
    (map #(assoc % :count num-victory-cards) cards)))

(defn num-curses [num-players]
  (case num-players
    4 30
    3 20
    10))

(defn start [num-players db]
  (-> db
      (assoc :players (repeat num-players cards/player))
      (update-in [:supply :treasure] adjust-count :copper (* num-players -7))
      (update-in [:supply :victory] adjust-victory-count num-players)
      (update-in [:supply :victory] set-count :curse (num-curses num-players))))
