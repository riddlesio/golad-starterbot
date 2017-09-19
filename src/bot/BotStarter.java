/*
 * Copyright 2017 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package bot;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import move.AbstractMove;
import move.BirthMove;
import move.KillMove;
import move.PassMove;

/**
 * bot.BotStarter - Created on 24-2-17
 *
 * Magic happens here. You should edit this file, or more specifically
 * the doMove() method to make your bot do more than random moves.
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class BotStarter {

    private Random random;

    private BotStarter() {
        this.random = new Random();
    }

    /**
     * Performs a Birth or a Kill move, currently returns a random move.
     * Implement this to make the bot smarter.
     * @param state The current state of the game
     * @return A Move object
     */
    AbstractMove doMove(BotState state) {
        AbstractMove move;
        double pMoveType = this.random.nextDouble();
        HashMap<String, ArrayList<Point>> cellMap = state.getField().getCellMapping();

        if (pMoveType < 0.5) {
            move = doRandomBirthMove(state, cellMap);
        } else {
            move = doRandomKillMove(state, cellMap);
        }

        return move;
    }

    /**
     * Selects one dead cell and two of own living cells a random to birth a new cell
     * on at the point of the dead cell
     * @param state Current bot state
     * @param cellMap A mapping of the points on the field by cell type
     * @return A random BirthMove or a PassMove if no BirthMove is possible
     */
    private AbstractMove doRandomBirthMove(BotState state, HashMap<String, ArrayList<Point>> cellMap) {
        ArrayList<Point> deadCells = cellMap.get(".");
        ArrayList<Point> myCells = new ArrayList<>(cellMap.get(state.getField().getMyId()));

        if (deadCells.size() <= 0 || myCells.size() < 2) {
            return doRandomKillMove(state, cellMap);
        }

        int birthIndex = this.random.nextInt(deadCells.size());
        Point randomBirth = deadCells.get(birthIndex);

        ArrayList<Point> sacrificePoints = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int sacrificeIndex = this.random.nextInt(myCells.size());
            Point randomSacrifice = myCells.remove(sacrificeIndex);
            sacrificePoints.add(randomSacrifice);
        }

        return new BirthMove(randomBirth, sacrificePoints);
    }

    /**
     * Selects one living cell on the field and kills it
     * @param state Current bot state
     * @param cellMap A mapping of the points on the field by cell type
     * @return A random KillMove or a PassMove if no KillMove is possible
     */
    private AbstractMove doRandomKillMove(BotState state, HashMap<String, ArrayList<Point>> cellMap) {
        String myId = state.getField().getMyId();
        String opponentId = state.getField().getOpponentId();

        List<Point> livingCells = Stream.
                concat(cellMap.get(myId).stream(), cellMap.get(opponentId).stream()).
                collect(Collectors.toList());

        if (livingCells.size() <= 0) {
            return new PassMove();
        }

        int killIndex = this.random.nextInt(livingCells.size());
        Point randomLiving = livingCells.get(killIndex);

        return new KillMove(randomLiving);
    }

    public static void main(String[] args) {
        BotParser parser = new BotParser(new BotStarter());
        parser.run();
    }
}
