import React, {Component} from 'react';
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css'
import {BrowserRouter as Router, Route} from 'react-router-dom'
import './App.css';
import Goods from './component/Goods';
import Header from './component/Header';
import CartList from "./component/CartList";

class App extends Component {

    render() {
        return (
            <div>
                <Router>
                    <Header/>
                    <div className="App ui container">
                        <Route exact path="/" component={Goods}/>
                        <Route exact path="/cart" component={CartList}/>
                        <ToastContainer autoClose={3000}/>
                    </div>
                </Router>
            </div>
        )
    }
}

export default App;
