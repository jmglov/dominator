(ns dominator.events
  (:require [dominator.db :as db]
            [dominator.game :as game]
            [re-frame.core :as rf]
            [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]))

(rf/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(rf/reg-event-db
 ::start-game
 (fn-traced [db _]
            (let [num-players 2]
              (->> db
                   (game/start num-players)
                   (game/start-turn (rand-int num-players))))))

(rf/reg-event-db
 ::next-phase
 (fn-traced [db _]
            (game/next-phase db)))
