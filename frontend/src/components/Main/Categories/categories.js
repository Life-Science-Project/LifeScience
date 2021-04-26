import React from "react";
import Preloader from  '../../common/Preloader/preloader'
import "./categories.css"
import PropTypes from 'prop-types';
import {byField} from "../../../utils/common";
import Category from "./Category/category";
import Article from "./Category/article";

const Categories = (props) => {
    if (props.category === null || props.category === undefined) {
        return <Preloader/>;
    }

    return (
        <div>
            <div className="category_name">
                {props.category.name}
            </div>
            <div className="categories_container">
                {props.category.subcategories.sort(byField('order')).map(category => <Category category={category}/>)}
            </div>
            <div className="articles_container">
                {props.category.articles.map(article => <Article article={article}/>)}
            </div>
        </div>
    );
};

Categories.propTypes = {
    category: PropTypes.exact({
        id: PropTypes.number.isRequired,
        parentId: PropTypes.oneOfType([
            PropTypes.number,
            null
        ]).isRequired,
        subcategories: PropTypes.arrayOf(
            PropTypes.exact({
                id: PropTypes.number.isRequired,
                name: PropTypes.string.isRequired,
                order: PropTypes.number.isRequired
            })
        ).isRequired,
        articles: PropTypes.arrayOf(
            PropTypes.exact({
                id: PropTypes.number.isRequired,
                version: PropTypes.exact({
                    name: PropTypes.string.isRequired,
                    articleId: PropTypes.number.isRequired,
                    sectionsIds: PropTypes.arrayOf(
                        PropTypes.exact({
                            id: PropTypes.number,
                            name: PropTypes.string
                        })
                    ).isRequired
                })
            })
        ).isRequired
    })
}

export default Categories;