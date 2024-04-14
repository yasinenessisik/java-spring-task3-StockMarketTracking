import React, { useEffect, useState } from 'react';
import io from "socket.io-client";
import Header from "./Header";
import Overview from "./Overview";
import Chart from "./Chart";
import Details from "./Details";

const Dashboard = () => {
    const [stocks, setStocks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [stockData, setStockData] = useState(null);

    useEffect(() => {
        const room = "Google";

        const socket = io("http://172.20.10.2:8085", {
            reconnection: false,
            query: `room=${room}`,
        });

        socket.on("get_single_stock_yearly", (res) => {
            console.log(res); // Tek bir obje geldiği varsayıldı
            setStockData(res); // Tek bir objeyi set et
            console.log(res.name); // Tek bir objenin name alanına eriş
        });

        return () => {
            socket.disconnect();
        };
    }, []);


    return (
        <div className={`h-screen grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 grid-rows-8 md:grid-rows-7 xl:grid-rows-5 auto-rows-fr gap-6 p-10 font-quicksand bg-neutral-100`}>
            <div className="col-span-1 md:col-span-2 lg:col-span-3 row-span-1">
                <Header />
            </div>
            <div className="md:col-span-2 row-span-4">
                {stockData && stockData.stockHistory && stockData.stockHistory.length > 0 && (
                <Chart stockHistory={stockData.stockHistory}/>)
                }
            </div>
            <div>
                {stockData && stockData.stockHistory && stockData.stockHistory.length > 0 && (
                    <Overview
                        change={100}
                        changePercent={stockData.stockHistory[stockData.stockHistory.length - 1].percentageChange}
                        price={stockData.stockHistory[stockData.stockHistory.length - 1].currentValue}
                    />
                )}

            </div>
            <div className="row-span-2 lg:row-span-3">
                {stockData && (
                    <Details name={stockData.name} />
                )}
            </div>
        </div>
    );

}

export default Dashboard;
