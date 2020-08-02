import React, {Component} from 'react';
import {ToastContainer} from "react-toastify"
import './App.css';
import Goods from './component/Goods';

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }

    render() {
        return (
            <div className="App ui container">
                <h1 className="ui block header">
                    <i className="home icon"/>
                </h1>
                <Goods/>
                <ToastContainer autoClose={4000}/>
            </div>
        )
    }

}

export default App;
