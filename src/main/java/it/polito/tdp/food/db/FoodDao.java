package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Coppia;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Food> getFoodByPortion(int porzioni){
		String sql = "SELECT f.* "
				+ "FROM food_pyramid_mod.portion p, food f "
				+ "WHERE f.food_code = p.food_code "
				+ "GROUP BY p.food_code "
				+ "HAVING COUNT(p.portion_id) <= ? "
				+ "ORDER BY f.display_name";
		List<Food> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, porzioni);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					result.add(new Food(res.getInt("food_code"),
							res.getString("display_name")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return result ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Coppia> getCalorieCongiunte(Map<Integer, Food> idMap, int porzioni){
		String sql = "SELECT fc1.food_code AS f1, fc2.food_code AS f2, AVG(c1.condiment_calories) AS peso "
				+ "FROM food_pyramid_mod.portion p1, food_pyramid_mod.portion p2, "
				+ "	condiment c1, condiment c2, food_condiment fc1, food_condiment fc2 "
				+ "WHERE p1.food_code = fc1.food_code "
				+ "AND p2.food_code = fc2.food_code "
				+ "AND fc1.food_code > fc2.food_code "
				+ "AND fc1.condiment_code = c1.condiment_code "
				+ "AND fc2.condiment_code = c2.condiment_code "
				+ "AND c1.condiment_code = c2.condiment_code "
				+ "GROUP BY fc1.food_code, fc2.food_code "
				+ "HAVING fc1.food_code IN "
				+ "(SELECT p.food_code "
				+ "FROM food_pyramid_mod.portion p "
				+ "GROUP BY p.food_code "
				+ "HAVING COUNT(p.portion_id) <= ?) "
				+ "AND fc2.food_code IN "
				+ "(SELECT p.food_code "
				+ "FROM food_pyramid_mod.portion p "
				+ "GROUP BY p.food_code "
				+ "HAVING COUNT(p.portion_id) <= ?)";
		List<Coppia> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, porzioni);
			st.setInt(2, porzioni);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					Food f1 = idMap.get(res.getInt("f1"));
					Food f2 = idMap.get(res.getInt("f2"));
					
					result.add(new Coppia(f1, f2, res.getDouble("peso")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			res.close();
			conn.close();
			return result ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
}
