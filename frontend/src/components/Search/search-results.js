import React from "react";
import Article from "./Results/article";
import "./search-results.css"
import Content from "./Results/content";

const SearchResults = ({results, query}) => {

    const ARTICLE = "Article";
    const CONTENT = "Content";

    const getListItem = (result) => {
        switch (result.typeName) {
            case ARTICLE : {
                return <Article name={result.name} articleId={result.articleId}/>
            }
            case CONTENT: {
                return <Content text={result.text}
                                articleId={result.articleId}
                                sectionId={result.sectionId}/>
            }
            default :
                return (
                    <div>
                        {result.text}
                    </div>
                )
        }
    }

    const articles = results.filter(res => res.typeName === ARTICLE);
    const contents = results.filter(res => res.typeName === CONTENT);

    return (
        <div className="search-results__main">
            <h4>
                Found {results.length} results for query "{query}" {results.length > 0 && ":"}
            </h4>
            {results.length > 0 && (
                <>
                    {articles.length > 0 &&
                    (<div className="search-results__type">
                        <h5>
                            Articles:
                        </h5>
                        <ul className="search-results__result-list">
                            {
                                articles.map(result =>
                                    <li className="search-results__result">
                                        {getListItem(result)}
                                    </li>
                                )
                            }
                        </ul>
                    </div>)}
                    {contents.length > 0 &&
                    (<div className="search-results__type">
                        <h5>
                            Contents:
                        </h5>
                        <ul className="search-results__result-list">
                            {
                                contents.map(result =>
                                    <li className="search-results__result">
                                        {getListItem(result)}
                                    </li>
                                )
                            }
                        </ul>
                    </div>)}
                </>
            )
            }
        </div>
    )

}

SearchResults.defaultProps = {
    results: [],
    query: "",
}

export default SearchResults