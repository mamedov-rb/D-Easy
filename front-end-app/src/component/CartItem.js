import React, {Component} from 'react'
import {connect} from "react-redux";

class CartItem extends Component {

    constructor(props) {
        super(props);
        this.state = {
            key: this.props.key
        }
    }

    render() {
        let cartItem = this.props.el;

        let plusClicked = () => {
            cartItem.quantity += 1;
            this.props.addToCart(cartItem);
        }

        let minusClicked = () => {
            if (cartItem.quantity > 1) {
                cartItem.quantity -= 1;
            }
            this.props.addToCart(cartItem);
        }

        return (
            <tr>
                <td>
                    <h4 className="ui image header">
                        <img src="/images/avatar2/small/lena.png" className="ui mini rounded image"/>
                        <div className="content">
                            {cartItem.name}
                        </div>
                    </h4>
                </td>
                <td>
                    {cartItem.quantity}
                </td>
                <td>
                    <button className="ui basic tiny icon button" onClick={plusClicked}>
                        <i className="plus icon"/>
                    </button>
                    <button className="ui basic tiny icon button" onClick={minusClicked}>
                        <i className="minus icon"/>
                    </button>
                    <button className="ui basic tiny icon button" onClick={this.props.removeItem}>
                        <i className="trash red icon"/>
                    </button>
                </td>
            </tr>
        )
    }
}

export default connect(
    state => ({
        cart: state.cart
    }),
    dispatch => ({
        addToCart: (cartItem) => {
            dispatch({type: 'ADD_TO_CART', payload: cartItem});
        }
    })
)(CartItem);