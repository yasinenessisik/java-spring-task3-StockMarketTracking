import React from "react";
import Card from "./Card";

const Overview = ({ price, changePercent }) => {
    return (
        <Card>
            <span className="absolute left-4 top-4 text-neutral-400 text-lg xl:text-xl 2xl:text-2xl">
            </span>
            <div className="w-full h-full flex items-center justify-around">
                <span className="text-2xl xl:text-4xl 2xl:text-5xl flex items-center">
                    ${price}
                    <span className="text-lg xl:text-xl 2xl:text-2xl text-neutral-400 m-2">
                    </span>
                </span>
                <span
                    className={`text-lg xl:text-xl 2xl:text-2xl ${
                        changePercent > 0 ? "text-lime-500" : "text-red-500"
                    }`}
                >
                    ({changePercent}%)
                </span>
            </div>
        </Card>
    );
};

export default Overview;
