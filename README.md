# Stock Tracking Application Development Task

## Status : Completed
  - Socketio
  - JPA
  - SQL
  - ReCharts
  - TCP

## Task Description
Develop a professional-grade stock tracking application with seamless connectivity, emphasizing continuous uptime and reliability.

## Key Requirements
- Create an application for tracking stocks and their historical data.
- Ensure uninterrupted connectivity to stock data sources.
- Design a user-friendly frontend interface for easy access and interaction.
- Utilize Java Persistence API (JPA) for data management and storage.
- Implement One-to-Many relationship between Stock and StockHistory entities.
- Ensure cascading operations and eager fetching for efficient data retrieval.

## Frontend Development Request
Request the development of a simple frontend interface to complement the backend stock tracking application.
- The frontend should provide users with intuitive access to stock information and historical data.
- Design the frontend to be responsive and visually appealing for a seamless user experience.
- Include features for viewing stock details, historical data, and graphical representations of stock performance.

## Field Definitions
- **Stock Entity:**
  - **stockID:** Unique identifier for each stock.
  - **name:** Name of the stock.
  - **stockHistory:** List of historical data for the stock.

- **StockHistory Entity:**
  - **stockHistoryID:** Unique identifier for each stock history entry.
  - **currentValue:** Current value of the stock.
  - **changeDirection:** Enum indicating the direction of change (e.g., increase, decrease, no change).
  - **percentageChange:** Percentage change in stock value.
  - **localDateTime:** Date and time of the stock data entry.
  - **stock:** Reference to the associated stock entity.

## Additional Notes
- Emphasize the importance of continuous connectivity and reliability in accessing stock data.
- Prioritize seamless user experience in both backend functionality and frontend interface design.
- Ensure adherence to proper software engineering practices, including robust error handling and testing.
- Provide comprehensive documentation for setup, usage, and maintenance of the application.

## Conclusion
This task entails the development of a professional stock tracking application with a focus on uninterrupted connectivity and user-friendly interaction. By implementing the specified backend entities and frontend interface, the goal is to deliver a high-quality application that meets the requirements of both stock tracking functionality and user experience.
## Result 
![Stock Tracking Application Result](https://github.com/yasinenessisik/java-spring-task3-StockMarketTracking/blob/main/result.gif)
