import React, { useState, useEffect } from 'react';
import Card from "./Card";
import { Area, AreaChart, ResponsiveContainer, Tooltip, XAxis, YAxis, LinearGradient, Stop } from "recharts";
import {chartConfig} from "../ChartConfig";
import ChartFilter from "./ChartFilter";

const Chart = ({ stockHistory }) => {
    const [filter, setFilter] = useState("1W");
    const [data, setData] = useState([]);

    useEffect(() => {
        if (stockHistory.length > 0) {
            // Map stock history data to chart data format
            const chartData = stockHistory.map(item => ({
                date: new Date(item.localDateTime * 1000), // Convert UNIX timestamp to JavaScript Date object
                value: item.currentValue
            }));
            setData(chartData);
        }
    }, [stockHistory]);

    return (
        <Card>
            <ul className="flex absolute top-2 right-2 z-40">
                {Object.keys(chartConfig).map((item) => (
                    <li key={item}>
                        <ChartFilter
                            text={item}
                            active={filter === item}
                            onClick={() => {
                                setFilter(item);
                            }}
                        />
                    </li>
                ))}
            </ul>
            <ResponsiveContainer>
                <AreaChart data={data}>
                    <defs>
                        <linearGradient id="colorValue" x1="0" y1="0" x2="0" y2="1">
                            <stop offset="5%" stopColor="#d49a13" stopOpacity={0.8}/>
                            <stop offset="95%" stopColor="#d49a13" stopOpacity={0}/>
                        </linearGradient>
                    </defs>
                    <Area type="monotone" dataKey="value" stroke="#d49a13" fill="url(#colorValue)" fillOpacity={1}
                          strokeWidth={0.5}/>
                    <Tooltip/>
                    <XAxis dataKey="date"/>
                    <YAxis domain={['dataMin', 'dataMax']}/>
                </AreaChart>
            </ResponsiveContainer>
        </Card>
    );
};

export default Chart;
