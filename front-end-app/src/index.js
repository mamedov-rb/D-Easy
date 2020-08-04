import React from 'react';
import 'semantic-ui-css/semantic.min.css';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux'
import {createStore} from 'redux'

import App from './App';

const REDUX_DEV_TOOLS = window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__();

function goods(state = [], action) {
    if (action.type === 'ADD_GOOD') {
        return [...state, action.payload];
    }
    return state;
}

const goodsStore = createStore(goods, REDUX_DEV_TOOLS);

ReactDOM.render(
    <Provider store={goodsStore}>
        <App/>
    </Provider>,
    document.getElementById('root')
);
