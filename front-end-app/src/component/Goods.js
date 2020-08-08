import React, {Component} from 'react'
import jsonStream from 'can-ndjson-stream';
import {toast} from "react-toastify";
import Good from './Good';
import {connect} from "react-redux";

const BASE_URL = 'http://localhost:8120/api/';
const ALL_POSITIONS_URL = 'good/all';

class Goods extends Component {

    componentDidMount() {
        this.fetchGoods()
    }

    fetchGoods = () => {
        fetch(BASE_URL + ALL_POSITIONS_URL)
            .then((response) => {
                return jsonStream(response.body);
            })
            .then((stream) => {
                const reader = stream.getReader();
                let read;
                reader.read().then(read = (result) => {
                    if (result.done) {
                        return;
                    }
                    this.props.addGood(result.value)
                    reader.read().then(read);
                });
            })
            .catch((err) => {
                toast.error(err.message, {position: toast.POSITION.TOP_RIGHT})
            });
    }

    render() {
        return (
            <div>
                <div className="ui five doubling link cards">
                    {this.props.goods.map((el, index) => {
                        return (
                            <Good key={index} el={el}/>
                        )
                    })}
                </div>
            </div>
        )
    }

}

export default connect(
    state => ({
        goods: state.goods
    }),
    dispatch => ({
        addGood: (good) => {
            dispatch({type: 'ADD_GOOD', payload: good});
        }
    })
)(Goods);