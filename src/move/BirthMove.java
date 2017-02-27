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

package move;

import java.awt.Point;
import java.util.ArrayList;

/**
 * move.BirthMove - Created on 24-2-17
 *
 * Used to output a birth move to the engine
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class BirthMove extends AbstractMove {

    private Point birthPoint;
    private ArrayList<Point> sacrificePoints;

    public BirthMove(Point birthPoint, ArrayList<Point> sacrificePoints) {
        this.moveType = MoveType.BIRTH;
        this.birthPoint = birthPoint;
        this.sacrificePoints = sacrificePoints;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.moveType.toString()).
                append(" ").
                append(pointToString(this.birthPoint));

        for (Point point : this.sacrificePoints) {
            builder.append(" ").
                    append(pointToString(point));
        }

        return builder.toString();
    }
}
