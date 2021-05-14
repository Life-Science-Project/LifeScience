import React from "react";
import Preloader from  '../../common/Preloader/preloader'
import "./categories.css"
import PropTypes from 'prop-types';
import {byField} from "../../../utils/common";
import Category from "./Category/category";
import Article from "./Category/article";
import Error from "../../common/Error/error";
import {ROLES} from "../../../constants";

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
        if (this.props.error) {
            return <Error error={this.props.error}/>
        }

        if (!this.props.category) {
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
            if (this.props.isAuthorized && this.props.category.subcategories.length === 0
                && this.props.userRoles
                && (this.props.userRoles.includes(ROLES.admin) || this.props.userRoles.includes(ROLES.moderator))) {
                return(
                    <button className="add_method_button" onClick={this.onAdd}>
                        Add Method
                    </button>
                )
            }
        }

        const showButton = () => {
            if (this.props.isAuthorized || this.props.category.parentId !== null) {
                return(
                    <div className="buttons_container">
                        <div className="return_button">
                            {buttonToPrevCategory()}
                        </div>
                        <div className="add_button">
                            {addButton()}
                        </div>
                    </div>
                );
            }
        }

        const showingInformation = () => {
            if (this.props.category.subcategories.length === 0) {
                return (
                    <div className="articles_container">
                        {this.props.category.articles.filter(x => x.version !== null).map(article => <Article key={article.id} article={article}/>)}
                    </div>
                );
            }

            return (
                <div className="categories_container">
                    {this.props.category.subcategories.sort(byField('order')).map(category => <Category key={category.id} category={category}/>)}
                </div>
            );
        }

        return (
            <div>
                {showButton()}
                <div className="category_name">
                    {this.props.category.name}
                </div>
                {showingInformation()}
            </div>
        );
    }
}

Categories.propTypes = {
    category: PropTypes.shape({
        id: PropTypes.number.isRequired,
        parentId: PropTypes.oneOfType([
            PropTypes.number,
            PropTypes.instanceOf(null)
        ]).isRequired,
        subcategories: PropTypes.arrayOf(
            PropTypes.shape({
                id: PropTypes.number.isRequired,
                name: PropTypes.string.isRequired,
                order: PropTypes.number.isRequired
            })
        ).isRequired,
        articles: PropTypes.arrayOf(
            PropTypes.shape({
                id: PropTypes.number.isRequired,
                version: PropTypes.shape({
                    name: PropTypes.string.isRequired,
                    id: PropTypes.number.isRequired,
                    sections: PropTypes.arrayOf(
                        PropTypes.shape({
                            id: PropTypes.number,
                            name: PropTypes.string,
                            order: PropTypes.number
                        })
                    ).isRequired
                })
            })
        ).isRequired
    })
}

export default Categories;
