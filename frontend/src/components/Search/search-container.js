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
    }, [location.search, results])

    const updateSearch = () => {
        setQuery(new URLSearchParams(location.search).get("query"))
        dispatch(search(query))
    }


    return (
        <SearchResults results={results} query={query}/>
    )
}

export default withRouter(SearchContainer)