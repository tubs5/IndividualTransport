package org.example.indivudualtransport.Model.bing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tobias Heidlund
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Point {
    double[] coordinates;

    public Point subtract(Point point2){
        return new Point(new double[]{coordinates[0] - point2.coordinates[0], coordinates[1] - point2.coordinates[1]});
    }

    public Point calcCenter(Point point2) {
        return new Point(new double[]{coordinates[0] + ((coordinates[0] - point2.coordinates[0])/2.0), coordinates[1]+((coordinates[1] - point2.coordinates[1])/2)});
    }

    @Override
    public String toString() {
        return coordinates[0] +", "+coordinates[1];
    }
}
