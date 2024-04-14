import React from 'react';
import Search from "./Search";

const Header = () => {

    return(
        <div className="xl: px-32">
            <h1 className="text-5xl"> Stock Market</h1>
            <Search></Search>
        </div>
    );
};
export default Header;