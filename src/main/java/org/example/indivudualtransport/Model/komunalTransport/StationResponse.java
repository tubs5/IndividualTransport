package org.example.indivudualtransport.Model.komunalTransport;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationResponse {

    private Station station;

    private double dist;

}