import React, {useState} from "react";
import {XIcon,SearchIcon} from "@heroicons/react/solid";
import SearchResults from "./SearchResults";

const Search = () => {
    const [input, setInput] = useState("");

    const [bestMatches, setBestMatches] = useState([]);

    const clearSearch = () => {
        setInput("")
        setBestMatches([])
    }
    const updateBestMatches = async () => {
    };

    return (
        <div
            className={`flex items-center my-4 border-2 rounded-md relative z-50 w-96`}
        >
            <input
                type="text"
                value={input}
                className={`w-full px-4 py-2 focus:outline-none rounded-md`}
                placeholder="Search stock..."
                onChange={(event) => setInput(event.target.value)}
                onKeyPress={(event) => {
                    if (event.key === "Enter") {
                        updateBestMatches();
                    }
                }}
            />
            {input && (<button onClick={clearSearch}>
                <XIcon className="h-4 w-4 fill-black"></XIcon>
            </button>)
            }
            <button onClick={updateBestMatches}
                    className="h-8 w-8 bg-amber-600 rounded-md flex justify-center items-center m-1 p-2">
            >
            <SearchIcon className="h-4 w-4 fill-black"></SearchIcon>
                {input &&  bestMatches.length > 0 ? (
                    <SearchResults results={bestMatches}/>
                ) : null}
            </button>
        </div>

    )

}
export default Search;