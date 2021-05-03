import React from "react";
import Article from "./Results/article";
import "./search-results.css"

const SearchResults = ({results, query}) => {

    const ARTICLE = "Article";
    const SECTION = "Section";

    const getListItem = (result) => {
        switch (result.typeName) {
            case ARTICLE : {
                return <Article name={result.text} articleId={result.id}/>
            }
            default :
                return (
                    <div>
                        {result.text}
                    </div>
                )
        }
    }


    return (
        <div className="search-results__main">
            <h4>
                Found {results.length} results for query "{query}" {results.length > 0 && ":"}
            </h4>
            <ul className="search-results__result-list">
                {
                    results.map(result =>
                        <li className="search-results__result">
                            {getListItem(result)}
                        </li>
                    )
                }
            </ul>
        </div>
    )

}

SearchResults.defaultProps = {
    results: [],
    query: "",
}

export default SearchResults