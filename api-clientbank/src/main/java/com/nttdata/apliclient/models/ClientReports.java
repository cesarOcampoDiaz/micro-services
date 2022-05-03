package com.nttdata.apliclient.models;

import com.nttdata.apliclient.document.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientReports {

    Client client;
    ClientProducts clientProducts;

}
