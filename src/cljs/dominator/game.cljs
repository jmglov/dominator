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

(defn face-up [card]
  (assoc card :face-up? true))

(defn face-down [card]
  (assoc card :face-up? false))

(defn discard->deck [{:keys [discard] :as player}]
  (-> player
      (assoc :deck (shuffle (map face-down discard))
             :discard [])))

(defn draw-cards [{:keys [deck hand discard] :as player} num]
  (if (= 0 (count deck) (count discard))
    player
    (let [drawn-cards (take num deck)
          num-drawn-cards (count drawn-cards)
          player (-> player
                     (update :hand concat drawn-cards)
                     (update :deck #(drop num %)))]
      (if (= num num-drawn-cards)
        player
        (draw-cards (discard->deck player) (- num num-drawn-cards))))))

(defn end-turn [{:keys [discard in-play] :as player}]
  (-> player
      (assoc :discard (concat in-play discard))
      (merge (draw-cards player 5))))

(defn start [num-players db]
  (-> db
      (assoc :game-started? true)
      (assoc :players (vec (repeat num-players (end-turn cards/player))))
      (assoc :current-player (rand-int num-players))
      (update-in [:supply :treasure] adjust-count :copper (* num-players -7))
      (update-in [:supply :victory] adjust-victory-count num-players)
      (update-in [:supply :victory] set-count :curse (num-curses num-players))))
