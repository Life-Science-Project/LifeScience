import React, {useState} from "react";
import {Link, useLocation, withRouter} from "react-router-dom";

const SearchForm = (props) => {

    const location = useLocation()
    const [query, setQuery] = useState("")

    const handleClick = (e) => {
        e.preventDefault()
        const trimmedQuery = query.trim();
        setQuery(trimmedQuery)
        if (trimmedQuery === "") {
            return
        }
        const params = new URLSearchParams(location.search);
        if (params.has("query")) {
            params.set("query", trimmedQuery)
        } else {
            params.append("query", trimmedQuery)
        }
        props.history.push(`/search?${params}`)
    }

    const handleChange = (e) => {
        setQuery(e.target.value)
    }

    return (
        <form className="form-inline ml-auto p-2">
            <input className="form-control mr-sm-2 "
                   type="search"
                   placeholder="Search"
                   aria-label="Search"
                   value={query}
                   onChange={handleChange}/>
            <Link to="/search">
                <button className="btn btn-outline-success my-2 my-sm-0"
                        type="submit"
                        onClick={handleClick}>
                    Search
                </button>
            </Link>
        </form>
    )
}

export default withRouter(SearchForm)