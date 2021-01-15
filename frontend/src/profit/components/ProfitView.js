import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import { fetchProfit } from '../actions';

class ProfitView extends React.Component {
  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.fetchProfit(id);
  }

  render() {
    if (!this.props.profit) {
      return <div>Loading...</div>;
    }

    //const { instituteName, profitNo } = this.props.profit;

    return (
      <div>
        <h1>Profit</h1>
        <h3>TODO:-</h3>
        <Link to="/profits" className="ui button primary">
          Back to List
        </Link>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { profit: state.profits[ownProps.match.params.id] };
};

export default connect(mapStateToProps, { fetchProfit })(ProfitView);
