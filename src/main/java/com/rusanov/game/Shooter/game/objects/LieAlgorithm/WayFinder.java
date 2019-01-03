package com.rusanov.game.Shooter.game.objects.LieAlgorithm;

import com.rusanov.game.Shooter.game.Constants;
import com.rusanov.game.Shooter.game.objects.GameObject;
import com.rusanov.game.Shooter.game.objects.GameObjectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WayFinder {
    private int[][] fillMap;
    private List<Point> stack = new ArrayList<>();
    private int rows = Constants.SCREEN_HEIGHT / Constants.TILE_SIZE;
    private int columns = Constants.SCREEN_WIDTH / Constants.TILE_SIZE;
    private int[][] coordinatesOfBlocks;

    public WayFinder(List<GameObject> objects) {
        fillMap = new int[rows][columns];
        getCoordinatesOfBlocks(objects);
    }

    private void getCoordinatesOfBlocks(List<GameObject> objects) {
        int numberOfBlocks = 0;
        for (GameObject object : objects) {
            if (object.getType() != GameObjectType.BLOCK) {
                break;
            }
            numberOfBlocks++;
        }
        coordinatesOfBlocks = new int[numberOfBlocks][4];
        for (int indexOfBlock = 0; indexOfBlock < numberOfBlocks; indexOfBlock++) {
            int x0 = (int) objects.get(indexOfBlock).getX() - objects.get(indexOfBlock).getWidth() / 2;
            int y0 = (int) objects.get(indexOfBlock).getY() - objects.get(indexOfBlock).getHeight() / 2;
            int x1 = x0 + objects.get(indexOfBlock).getWidth() - 1;
            int y1 = y0 + objects.get(indexOfBlock).getHeight() - 1;
            coordinatesOfBlocks[indexOfBlock][0] = x0;
            coordinatesOfBlocks[indexOfBlock][1] = y0;
            coordinatesOfBlocks[indexOfBlock][2] = x1;
            coordinatesOfBlocks[indexOfBlock][3] = y1;
        }
    }

    private void push(Point point, int wayLength) {
        if (wayLength < fillMap[point.getRow()][point.getColumn()]) {
            fillMap[point.getRow()][point.getColumn()] = wayLength;
            stack.add(point);
        }
    }

    private Point pop() {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.remove(0);
    }

    public Point[] find(Point start, Point end) {
        for (int[] rowFillMap : fillMap) {
            Arrays.fill(rowFillMap, Integer.MAX_VALUE);
        }

        Point point;
        int wayLength = 0;
        push(start, wayLength);
        while ((point = pop()) != null) {
            wayLength = fillMap[point.getRow()][point.getColumn()] + 1;
            if ((point.getRow() + 1 < rows) && isNotIntersectsWithBlock(point.getColumn(), point.getRow() + 1)) {
                push(new Point(point.getColumn(), point.getRow() + 1), wayLength);
            }
            if ((point.getRow() - 1 >= 0) && isNotIntersectsWithBlock(point.getColumn(), point.getRow() - 1)) {
                push(new Point(point.getColumn(), point.getRow() - 1), wayLength);
            }
            if ((point.getColumn() + 1 < columns) && isNotIntersectsWithBlock(point.getColumn() + 1, point.getRow())) {
                push(new Point(point.getColumn() + 1, point.getRow()), wayLength);
            }
            if ((point.getColumn() - 1 >= 0) && isNotIntersectsWithBlock(point.getColumn() - 1, point.getRow())) {
                push(new Point(point.getColumn() - 1, point.getRow()), wayLength);
            }

            if ((point.getRow() + 1 < rows) && (point.getColumn() + 1 < columns) &&
                    isNotIntersectsWithBlock(point.getColumn() + 1, point.getRow() + 1)) {
                push(new Point(point.getColumn() + 1, point.getRow() + 1), wayLength);
            }
            if ((point.getRow() + 1 < rows) && (point.getColumn() - 1 >= 0) &&
                    isNotIntersectsWithBlock(point.getColumn() - 1, point.getRow() + 1)) {
                push(new Point(point.getColumn() - 1, point.getRow() + 1), wayLength);
            }
            if ((point.getRow() - 1 >= 0) && (point.getColumn() + 1 < columns) &&
                    isNotIntersectsWithBlock(point.getColumn() + 1, point.getRow() - 1)) {
                push(new Point(point.getColumn() + 1, point.getRow() - 1), wayLength);
            }
            if ((point.getRow() - 1 >= 0) && (point.getColumn() - 1 >= 0) &&
                    isNotIntersectsWithBlock(point.getColumn() - 1, point.getRow() - 1)) {
                push(new Point(point.getColumn() - 1, point.getRow() - 1), wayLength);
            }
        }
        if (fillMap[end.getRow()][end.getColumn()] == Integer.MAX_VALUE) {
            return null;
        }

        return shortWay(start, end);
    }

    private boolean isNotIntersectsWithBlock(int column, int row) {
        int x = column * Constants.TILE_SIZE + Constants.TILE_SIZE / 2;
        int y = row * Constants.TILE_SIZE + Constants.TILE_SIZE / 2;
        for (int[] coordinatesOfBlock : coordinatesOfBlocks) {
            int x0 = coordinatesOfBlock[0] - ((Constants.HUMAN_SIZE - Constants.TILE_SIZE) / 2);
            int y0 = coordinatesOfBlock[1] - ((Constants.HUMAN_SIZE - Constants.TILE_SIZE) / 2);
            int x1 = coordinatesOfBlock[2] + ((Constants.HUMAN_SIZE - Constants.TILE_SIZE) / 2);
            int y1 = coordinatesOfBlock[3] + ((Constants.HUMAN_SIZE - Constants.TILE_SIZE) / 2);
            if (x >= x0 && x <= x1 && y >= y0 && y <= y1) {
                return false;
            }
        }
        return true;
    }

    private Point[] shortWay(Point start, Point end) {
        List<Point> way = new ArrayList<>();
        way.add(end);
        int column = end.getColumn();
        int row = end.getRow();
        int nextColumn = 0;
        int nextRow = 0;
        int pointLength = Integer.MAX_VALUE;
        while ((column != start.getColumn()) || (row != start.getRow())) {
            if ((row + 1 < rows) && (column + 1 < columns) && fillMap[row + 1][column + 1] < pointLength) {
                nextColumn = column + 1;
                nextRow = row + 1;
                pointLength = fillMap[row + 1][column + 1];
            }
            if ((row + 1 < rows) && (column - 1 >= 0) && fillMap[row + 1][column - 1] < pointLength) {
                nextColumn = column - 1;
                nextRow = row + 1;
                pointLength = fillMap[row + 1][column - 1];
            }
            if ((row - 1 >= 0) && (column + 1 < columns) && fillMap[row - 1][column + 1] < pointLength) {
                nextColumn = column + 1;
                nextRow = row - 1;
                pointLength = fillMap[row - 1][column + 1];
            }
            if ((row - 1 >= 0) && (column - 1 >= 0) && fillMap[row - 1][column - 1] < pointLength) {
                nextColumn = column - 1;
                nextRow = row - 1;
                pointLength = fillMap[row - 1][column - 1];
            }

            if ((row + 1 < rows) && (fillMap[row + 1][column] <= pointLength)) {
                nextColumn = column;
                nextRow = row + 1;
                pointLength = fillMap[row + 1][column];
            }
            if ((row - 1 >= 0) && (fillMap[row - 1][column] <= pointLength)) {
                nextColumn = column;
                nextRow = row - 1;
                pointLength = fillMap[row - 1][column];
            }
            if ((column + 1 < columns) && (fillMap[row][column + 1] <= pointLength)) {
                nextColumn = column + 1;
                nextRow = row;
                pointLength = fillMap[row][column + 1];
            }
            if ((column - 1 >= 0) && (fillMap[row][column - 1] <= pointLength)) {
                nextColumn = column - 1;
                nextRow = row;
                pointLength = fillMap[row][column - 1];
            }
            column = nextColumn;
            row = nextRow;
            way.add(new Point(column, row));
        }

        Point[] result = new Point[way.size()];
        int index = way.size() - 1;
        for (Point point : way) {
            result[index--] = point;
        }
        return result;
    }
}
