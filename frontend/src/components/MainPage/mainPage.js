import React from "react";
import './mainPage.css'
import axios from "axios";
import Section from "./Section/section";
import {Link} from "react-router-dom";
import {Redirect} from "react-router";


export default class Main extends React.Component {
    state = {
        isFetched : false,
        category : {
        }
    }

    constructor(props) {
        super(props);
        this.setState({id: props.match.params.id})
    }

    componentDidMount() {
        this.initialize();
    }

    initialize() {
        const id = this.state.id;
        const uri = 'http://localhost:8080/api/categories/';
        if (id === undefined) {
            axios.get(uri + 'root').then(res => {
                const category = res.data[0];
                this.setState({isFetched: true, category: category});
            })
        } else {
            axios.get(uri + id).then(res => {
                const category = res.data;
                this.setState({isFetched: true, category: category});
            })
        }
    }

    /*shouldComponentUpdate(nextProps, nextState, nextContext) {
        //alert(JSON.stringify(nextState.id));
        return this.state.id !== this.state.category.id
    }*/

    /*componentDidUpdate(prevProps, prevState, snapshot) {
        this.initialize();
    }*/

    render() {
        //this.initialize();
        return(<div>
                {this.state.isFetched ? (<div>
                                <div className="section_name">
                                    {this.state.category.name}
                                </div>
                                <div className="sections_container">
                                    {this.state.category.subcategories.map((section) => {/* <Section section={section}/>*/
                                    return this.section(section)})}
                                </div>
                </div>) : <div> Loading... </div>}
            </div>
        );
    }

    section(sec) {
        return(
            <div className="section_container">
                <Redirect to={"/main/" + sec.id}/>
                <Link to={"/main/" + sec.id} onClick={() => this.setState({id: sec.id})}>
                    {sec.name}
                </Link>
            </div>
        )
    }
}