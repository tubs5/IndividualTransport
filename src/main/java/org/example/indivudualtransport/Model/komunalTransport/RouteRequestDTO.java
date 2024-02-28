package org.example.indivudualtransport.Model.komunalTransport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//TODO test class
public class RouteRequestDTO {
    private String startPos;
    private String dest;
}