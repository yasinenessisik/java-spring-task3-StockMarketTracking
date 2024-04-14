import React from 'react';
import Card from "./Card";

const Details = ({ name }) => {
    return(
        <Card>
            <ul className={`w-full h-full flex flex-col justify-between divide-y-1`}>
                <li className="flex-1 flex justify-between items-center ">
                    <span>Hisse Senedi Adı:</span>
                    <span className="text-5xl">{name}</span>
                </li>
            </ul>
        </Card>
    );
};
export default Details;