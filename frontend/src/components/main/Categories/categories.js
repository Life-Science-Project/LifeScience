import React from "react";
import Preloader from  '../../common/Preloader/preloader'
import "./categories.css"
import PropTypes from 'prop-types';
import {byField} from "../../../utils/common";
import Category from "./Category/category";
import Article from "./Category/article";
import Trouble from "../../common/Trouble/trouble";

class Categories extends React.Component {
    constructor(props) {
        super(props);
        this.onReverse = this.onReverse.bind(this);
        this.onAdd = this.onAdd.bind(this);
    }

    onReverse() {
        this.props.history.push("/categories/" + this.props.category.parentId);
    }

    onAdd() {
        this.props.history.push("/new-article/" + this.props.category.id);
    }

    render() {
        if (this.props.trouble !== undefined && this.props.trouble !== null) {
            return <Trouble trouble={this.props.trouble}/>
        }

        if (this.props.category === null || this.props.category === undefined) {
            return <Preloader/>;
        }

        const buttonToPrevCategory = () => {
            if (this.props.category.parentId !== null) {
                return(
                    <button className="return_button" onClick={this.onReverse}>
                        Previous
                    </button>
                );
            }
        }

        const addButton = () => {
            if (this.props.isShowButton) {
                return(
                    <button className="add_method_button" onClick={this.onAdd}>
                        Add Method
                    </button>
                )
            }
        }

        return (
            <div>
                <div className="buttons_container">
                    {buttonToPrevCategory()}
                    {addButton()}
                </div>
                <div className="category_name">
                    {this.props.category.name}
                </div>
                <div className="categories_container">
                    {this.props.category.subcategories.sort(byField('order')).map(category => <Category category={category}/>)}
                </div>
                <div className="articles_container">
                    {this.props.category.articles.map(article => <Article article={article}/>)}
                </div>
            </div>
        );
    }
}

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