import React, {Component} from 'react'
import {connect} from "react-redux";
import CartItem from "./CartItem";
import {toast} from "react-toastify";
import jsonStream from 'can-ndjson-stream';

const BASE_URL = 'http://localhost:8000/api/';
const CREATE_ORDER_URL = 'order/create/';

class CartList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            cart: this.props.cart,
            consumerAddress: '',
            description: ''
        }
    }

    handleChange = (event) => {
        this.setState(
            {[event.target.name]: event.target.value}
        )
    }

    render() {
        let removeItem = (cartItem) => {
            this.props.removeFromCart(cartItem);
            this.setState({cart: this.props.cart})
        }

        return (
            <div>
                <table className="ui very basic celled table">
                    <thead>
                    <tr>
                        <th>Good name</th>
                        <th>Quantity</th>
                        <th>Configure</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.cart.map((el, index) => {
                        return (
                            <CartItem key={index} el={el} removeItem={removeItem}/>
                        )
                    })}
                    </tbody>
                </table>
                <div className="ui divider"/>
                <div className="ui segment form">
                    <div className="field">
                        <input type="text" name="consumerAddress" placeholder="Your address please.."
                               onChange={this.handleChange}/>
                    </div>
                    <div className="field">
                        <textarea rows="2" name="description" placeholder="If you have some wishes.."
                                  onChange={this.handleChange}/>
                    </div>
                    <button className="ui basic button" onClick={this.createOrder}>
                        create order
                    </button>
                </div>
            </div>
        )
    }

    createOrder = () => {
        let order = {
            consumerAddress: this.state.consumerAddress,
            description: this.state.description,
            organisationAddress: this.props.cart[0].organisationAddress,
            orderPositions: this.props.cart
        }
        let init = {
            method: 'post',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(order)
        };
        fetch(BASE_URL + CREATE_ORDER_URL, init)
            .then(response => jsonStream(response.body))
            .then((stream) => {
                const reader = stream.getReader();
                let read;
                reader.read().then(read = (result) => {
                    if (result.done) {
                        return;
                    }
                    toast.success("Order created, id: " + result.value.orderId, {position: toast.POSITION.TOP_RIGHT})
                    reader.read().then(read);
                });
            })
            .catch((err) => {
                toast.error(err.message, {position: toast.POSITION.TOP_RIGHT})
            })
    }

}

export default connect(
    state => ({
        cart: state.cart
    }),
    dispatch => ({
        addToCart: (good) => {
            dispatch({type: 'ADD_TO_CART', payload: good});
        },
        removeFromCart: (cartItem) => {
            dispatch({type: 'REMOVE_FROM_CART', payload: cartItem});
        }
    })
)(CartList);