package com.example.Job.Repository;

import com.example.Crypto.Model.Coin;
import com.example.Crypto.Model.Market;
import com.example.StudentsCoursesLectures.Model.DBParameters;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CryptoRepository {
    private static final String connectionString = DBParameters.getInstance().getConnectionString();
    public static final String username = DBParameters.getInstance().getUsername();
    public static final String password = DBParameters.getInstance().getPassword();

    public static List<Coin> getCoins() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DBParameters.getInstance().getConnectionString(), DBParameters.getInstance().getUsername(), DBParameters.getInstance().getPassword());
             Statement statement = connection.createStatement()) {

            final ResultSet resultSet = statement.executeQuery("SELECT * FROM crypto.coins");
            List<Coin> coins = new ArrayList<>();

            while (resultSet.next()) {
                Coin coin = new Coin(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                coins.add(coin);
            }

            return coins;
        }
    }

    public static void newMarketValue(Market market) throws SQLException {
        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {

            String query = "INSERT INTO crypto.market_history VALUES (DEFAULT, '" + market.getCoin().getCoinId() + "', " + market.getPrice() + ", '" + market.getDate() + "')";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
    }


}