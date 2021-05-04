import {useEffect, useState} from "react";
import {useLocation, withRouter} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {search} from "../../redux/search-reducer";
import SearchResults from "./search-results";

const SearchContainer = () => {

    const location = useLocation()
    const dispatch = useDispatch()

    const [query, setQuery] = useState("")
    const results = useSelector(state => state.search.results)

    useEffect(() => {
        updateSearch()
    }, [location.search])

    const updateSearch = () => {
        const newQuery = new URLSearchParams(location.search).get("query")
        setQuery(newQuery)
        dispatch(search(newQuery))
    }

    return (
        <SearchResults results={results} query={query}/>
    )
}

export default withRouter(SearchContainer)