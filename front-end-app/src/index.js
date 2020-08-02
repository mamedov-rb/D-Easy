import React from 'react';
import 'semantic-ui-css/semantic.min.css';
import ReactDOM from 'react-dom';
import App from './App';
import * as serviceWorker from './serviceWorker';

serviceWorker.unregister();

ReactDOM.render(<App />, document.getElementById('root'));