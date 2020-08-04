import React, {Component} from 'react';
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css'
import './App.css';
import Goods from './component/Goods';

class App extends Component {

    render() {
        return (
            <div>
                <h2 className="ui block header">
                    <div className="right icon floated">
                        <i className="cart icon"/>
                        <div className="ui red circular tiny label">22</div>
                    </div>
                </h2>
                <div className="App ui container">
                    <Goods/>
                    <ToastContainer autoClose={3000}/>
                </div>
            </div>
        )
    }
}

export default App;
