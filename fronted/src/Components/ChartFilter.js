import React from "react";

const ChartFilter = ({ text, active, onClick }) => {
    return (
        <button
            onClick={onClick}
            className={`w-12 m-2 h-8 border-1 rounded-md flex items-center justify-center cursor-pointer ${
                active
                    ? "bg-orange-500 border-orange-600 text-gray-100"
                    : "border-orange-400 text-orange-400"
            } transition duration-200 hover:bg-orange-600 hover:text-gray-100 hover:border-orange-700`}
        >
            {text}
        </button>
    );
};

export default ChartFilter;
