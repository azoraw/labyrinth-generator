package com.azoraw.game

import spock.lang.Specification
import spock.lang.Unroll

class CellTest extends Specification {

    @Unroll
    def "should get direction between head cell and neighbour cell"() {
        given:
        def headCell = new Cell(xHead, yHead)
        def neighbourCell = new Cell(xNeighbour, yNeighbour)

        expect:
        headCell.getDirection(neighbourCell) == directionResult

        where:
        xHead | yHead | xNeighbour | yNeighbour || directionResult
        5     | 5     | 6          | 5          || Direction.RIGHT
        5     | 5     | 4          | 5          || Direction.LEFT
        5     | 5     | 5          | 6          || Direction.DOWN
        5     | 5     | 5          | 4          || Direction.UP
    }
}
